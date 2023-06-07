#!/bin/bash

#build the spring-bot jar
#gradle clean processResources bootJar

for version in ./build/libs/*.jar; do
  VERSION=${version:27:5}
  echo ${VERSION}
done

#builds the tarBall and creates it under ./build/distributions/
gradle clean processResources bootJar assembleArtifact -P releaseTo='uat'

echo "accounts tar-ball is created under ./build/distributions/ on $(date +%Y%m%d:%H:%M:%S)"

# Execute setenv if it exist for taking environment variables.
if [ -f setenv.sh ]; then
    . setenv.sh
fi

echo "command is $1"
if [[ "$1" == "swaggerDoc" ]]; then

  echo "Now generating the swagger documentation !"
  java -jar ./build/libs/accounts-*.jar &

  echo "app running"
  for i in {1..20}; do
    echo "Running since seconds : $i"
    sleep 1
  done

  wget -O documents/api_document/api.json http://127.0.0.1:10010/accounts/api-docs --no-proxy

  PID=$(ps ax | grep java | grep "accounts-*" | awk '{print $1}')

  kill -9 ${PID}

  echo "Process killed and api.json saved, now generating Swagger 2.0 Documentation"

  # go to API directory and run redoc-cli module (it is an npm module 'redoc-cli')
  cd documents/api_document
  #spectacle --quiet --target-file accounts.html api.json
  redoc-cli bundle api.json --options.hide-download-button --options.required-props-first --options.path-in-middle-panel

  mv redoc-static.html accounts.html
  rm api.json

elif [[ "$1" == "run" ]]; then

  echo "Now running the Spring-boot !"
  java -jar ./build/libs/accounts-*.jar

elif [[ "$1" == "publish" ]]; then

  echo "Generating and Publishing documennts to KUNJI"
  ./generateAndPublishHtmlDocs.sh "accounts" "ACCOUNTS_${VERSION}" "publish"

fi
