#!/usr/bin/env bash

./gradlew dU|grep \\-\>|sort -u
