#!/bin/bash


export CSCARDS_ENDPOINT=https://app.clearscore.com/api/global/backend-tech-test/v1/cards
export SCOREDCARDS_ENDPOINT=https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards
export HTTP_PORT=8080
export HOST_ADDRESS=0.0.0.0
export USER_AGENT_ID=wojrmark-cardscores

sbt run