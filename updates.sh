#!/usr/bin/env bash

set -euo pipefail
cd ${0%/*}

echo "┌──────────────────────┐"
echo "│ Project dependencies │"
echo "└──────────────────────┘"
./gradlew dU |grep \\-\>|sort -u
