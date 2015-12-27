package service

import (
	"crypto/md5"
	"encoding/hex"
	"github.com/ant0ine/go-json-rest/rest"
	"log"
	"net/http"
)

const seed string = "fjdsfdssdrrrsd"

type LoginInfo struct {
	UserName string
	Password string
}

type AuthInfo struct {
	UserName string
	Token    string
}

func Login(w rest.ResponseWriter, r *rest.Request) {
	loginInfo := LoginInfo{}
	err := r.DecodeJsonPayload(&loginInfo)
	if err != nil {
		rest.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	if loginInfo.UserName == "" {
		rest.Error(w, "Login UserName is nil", http.StatusForbidden)
		return
	}
	if loginInfo.Password == "" {
		rest.Error(w, "Login Password is nil", http.StatusForbidden)
		return
	}
	log.Printf("auth user login user_name:%v password:%v", loginInfo.UserName, loginInfo.Password)
	authInfo := AuthInfo{UserName: loginInfo.UserName, Token: signature(loginInfo.UserName)}
	w.WriteJson(&authInfo)

}

func Auth(w rest.ResponseWriter, r *rest.Request) {
	authInfo := AuthInfo{}
	err := r.DecodeJsonPayload(&authInfo)
	if err != nil {
		rest.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	if authInfo.UserName == "" {
		rest.Error(w, "Auth UserName is nil", http.StatusForbidden)
		return
	}
	if authInfo.Token == "" {
		rest.Error(w, "Auth Token is nil", http.StatusForbidden)
		return
	}
	if comparedToken := signature(authInfo.UserName); comparedToken != authInfo.Token {
		rest.Error(w, "Auth failed", http.StatusUnauthorized)
		return
	}
	w.WriteHeader(http.StatusOK)
}

func signature(userName string) string {
	bArray := md5.Sum([]byte(userName + seed))
	return hex.EncodeToString(bArray[:])
}
