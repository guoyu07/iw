package service

import (
	"github.com/ant0ine/go-json-rest/rest"
	"log"
	"net/http"
)

type UserInfo struct {
	UserName string
	Password string
}

func AuthUser(w rest.ResponseWriter, r *rest.Request) {
	userInfo := UserInfo{}
	err := r.DecodeJsonPayload(&userInfo)
	if err != nil {
		rest.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	if userInfo.UserName == "" {
		rest.Error(w, "UserName is nil", http.StatusInternalServerError)
		return
	}
	if userInfo.Password == "" {
		rest.Error(w, "Password is nil", http.StatusInternalServerError)
		return
	}
	log.Printf("auth user login user_name:%v password:%v", userInfo.UserName, userInfo.Password)
}
