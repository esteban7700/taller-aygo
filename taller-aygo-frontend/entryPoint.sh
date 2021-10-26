#!/bin/bash
set -xe
: "${BACK_API?Need an api url}"

sed -i "s@BACK_API@$BACK_API@g" /usr/share/nginx/html/main*.js

exec "$@"
