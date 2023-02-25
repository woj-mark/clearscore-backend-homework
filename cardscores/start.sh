#!/bin/bash


export CSCARDS_ENDPOINT=https://app.clearscore.com/api/global/backend-tech-test/v1/cards
export SCOREDCARDS_ENDPOINT=https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards
export HTTP_PORT=8080
export USER_AGENT=wojrmark-cardscores

sbt run