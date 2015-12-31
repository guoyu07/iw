package service

import (
	"github.com/ant0ine/go-json-rest/rest"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
	// "log"
	"net/http"
)

func add(session *mgo.Session, spuInfo map[string]interface{}) error {
	c := session.DB("product").C("items")
	objectId := bson.NewObjectId()
	spuInfo["_id"] = objectId
	err := c.Insert(&spuInfo)
	if err != nil {
		return err
	}
	c = session.DB("product").C("list")
	listItem := makeListItem(spuInfo)
	err = c.Insert(&listItem)
	return err
}

func update(session *mgo.Session, spuInfo map[string]interface{}) error {
	c := session.DB("product").C("items")
	selector := bson.M{"_id": spuInfo["_id"]}
	err := c.Update(selector, &spuInfo)
	if err != nil {
		return err
	}
	listItem := makeListItem(spuInfo)
	c = session.DB("product").C("list")
	err = c.Update(selector, listItem)
	return err
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
	default:
		rest.Error(w, "spu Not Implemented Operation", http.StatusNotImplemented)
	}

	if err != nil {
		rest.Error(w, "spu Operater Error", http.StatusInternalServerError)
	}
}
