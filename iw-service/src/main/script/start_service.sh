#!/usr/bin/env bash
BINDIR=$(dirname $0)
cd "$BINDIR/.."
mkdir log
java -Dlog4j.configurationFile="conf/log4j2.xml" -DconfigFile=conf/config.json -cp "lib/*" cn.gaohongtao.Main