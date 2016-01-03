package service

import (
	"errors"
	"fmt"
	"github.com/ant0ine/go-json-rest/rest"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
	"log"
	"net/http"
)

var (
	NotFoundFieldError = errors.New("can find specail field")
)

func add(session *mgo.Session, spuInfo map[string]interface{}) error {
	c := session.DB("product").C("items")
	objectId := bson.NewObjectId()
	spuInfo["_id"] = objectId
	spuInfo["saleState"] = "on"
	spuInfo["onSaleDate"] = bson.Now()
	err := c.Insert(&spuInfo)
	if err != nil {
		return err
	}
	c = session.DB("product").C("list")
	makeListItem(spuInfo)
	err = c.Insert(spuInfo)
	return err
}

func update(session *mgo.Session, spuInfo map[string]interface{}) error {
	objectId := bson.ObjectIdHex(spuInfo["_id"].(string))
	delete(spuInfo, "_id")
	c := session.DB("product").C("items")
	selector := bson.M{"_id": objectId}
	err := c.Update(selector, bson.M{"$set": spuInfo, "$currentDate": bson.M{"lastModified": true}})
	if err != nil {
		return err
	}
	makeListItem(spuInfo)
	c = session.DB("product").C("list")
	err = c.Update(selector, bson.M{"$set": spuInfo, "$currentDate": bson.M{"lastModified": true}})
	return err
}

func toggleOnSale(session *mgo.Session, spuInfo map[string]interface{}) error {
	objectId, ok := spuInfo["_id"].(string)
	if !ok {
		return NotFoundFieldError
	}
	productDB := session.DB("product")
	itemsCol := productDB.C("items")
	item := make(map[string]interface{})
	err := itemsCol.FindId(objectId).One(item)
	if err != nil {
		return err
	}

	listCol := productDB.C("list")
	var existedItemSize int = 0
	existedItemSize, _ = listCol.FindId(objectId).Count()
	if existedItemSize > 0 {
		//off sale
		item["offSaleDate"] = bson.Now()
		item["saleState"] = "off"
		err := itemsCol.UpdateId(objectId, item)
		if err != nil {
			return err
		}
		return listCol.RemoveId(objectId)
	} else {
		//on sale
		item["onSaleDate"] = bson.Now()
		item["saleState"] = "on"
		err := itemsCol.UpdateId(objectId, item)
		if err != nil {
			return err
		}
		makeListItem(item)
		return listCol.Insert(item)
	}
}

func find(session *mgo.Session, scenior map[string]interface{}) (result []map[string]interface{}, err error) {
	c := session.DB("product").C("items")
	query, ok := scenior["query"].(map[string]interface{})
	if !ok {
		return nil, NotFoundFieldError
	}
	sort, ok := scenior["sort"].(string)
	if !ok {
		return nil, NotFoundFieldError
	}

	err = c.Find(query).Select(bson.M{"sizeList": 0, "itemStyle": 0, "desc": 0}).Sort(sort).All(&result)
	return result, err
}

func findOne(session *mgo.Session, query map[string]interface{}) (result map[string]interface{}, err error) {
	objectId, ok := query["_id"].(string)
	if !ok {
		return nil, NotFoundFieldError
	}
	log.Println(query)
	c := session.DB("product").C("items")
	err = c.FindId(bson.ObjectIdHex(objectId)).One(&result)
	return result, err
}

func makeListItem(spuInfo map[string]interface{}) map[string]interface{} {
	delete(spuInfo, "sizeList")
	delete(spuInfo, "itemStyle")
	delete(spuInfo, "desc")
	return spuInfo
}

func SpuHandle(w rest.ResponseWriter, r *rest.Request) {
	spuInfo := make(map[string]interface{})
	err := r.DecodeJsonPayload(&spuInfo)
	if err != nil {
		rest.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	session, err := mgo.Dial("mongodb.iwshoes.cn")
	if err != nil {
		panic(err)
	}
	defer session.Close()
	// Optional. Switch the session to a monotonic behavior.
	session.SetMode(mgo.Monotonic, true)

	switch r.PathParam("oper") {
	case "add":
		err = add(session, spuInfo)
	case "update":
		err = update(session, spuInfo)
	case "toggleOnSale":
		err = toggleOnSale(session, spuInfo)
	case "find":
		itemList, err := find(session, spuInfo)
		if err == nil {
			err = w.WriteJson(itemList)
		}
	case "findOne":
		itemList, err := findOne(session, spuInfo)
		if err == nil {
			err = w.WriteJson(itemList)
		}
	default:
		rest.Error(w, "spu Not Implemented Operation", http.StatusNotImplemented)
	}

	if err != nil {
		rest.Error(w, fmt.Sprintf("spu operation error:%s", err.Error()), http.StatusInternalServerError)
	}
}
