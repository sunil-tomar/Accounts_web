#!/bin/bash
#####################################################################################
## Author   : Sunil Tomar
## Date     : 07.06.2023
## Purpose  : This script generates the .html files based on .md files for publish.
#####################################################################################
#####################################################################################

PWD="$(pwd)"
MD_FILES="mdfiles.txt"
IMG_FILES="imagefiles.txt"
APP_NAME=${1}
PATCH_DIR=${2}
ACTION=${3}
CREATE_PATCH_DIR="1"
#KUNJI_HOST_CONN_STRING="root@10.128.131.182:/var/www/html/applicationDocs/"
#KUNJI_HOST_CONN_PWD="srvt123$"

## If MD5SUM is to be generated, then 1 else 0
GENERATE_MD5SUM="1"
## Location of build file whose MD5-SUM is to be calculated.
BUILD_FILE_LOCATION="${PWD}/build/distributions/*.tar"

validation() {
  TREE_EXISTS=$(which tree | awk '{print $1}')
  SSHPASS_EXISTS=$(which sshpass | awk '{print $1}')
  PANDOC_EXISTS=$(which pandoc | awk '{print $1}')
  PLANTUML_EXISTS=$(which plantuml | awk '{print $1}')

  if [ -z ${APP_NAME} ] || [ -z ${PATCH_DIR} ] || ! [[ ${ACTION} =~ verify|publish ]]; then
    echo "!! Script Usage is ./generateAndPublishHtmlDocs.sh <app-name> <app-tag-version> [verify | publish]"
    exit 1
  fi

  if [ -z ${TREE_EXISTS} ] || [[ -z ${SSHPASS_EXISTS} && ${PLATFORM} == "linux" ]] || [ -z ${PANDOC_EXISTS} ] || [ -z ${PLANTUML_EXISTS} ]; then
    echo "!! ERROR: tree, sshpass, plantuml or pandoc unix package is not present, please install and re-run."
    exit 1
  fi
}

generate_images_puml() {

  # generate all .puml files to imges via recursive calls.
  plantuml -r -tsvg documents*/**.puml

  cd documents || exit 1

  touch ${IMG_FILES}

  # prepare the files which needs to be genrated
  tree -if -P *.svg -I templates --prune | grep .svg >${IMG_FILES}

  while IFS= read -r line; do
    ## abosolute path
    SRC_ABSOLUTE_PATH=$(echo ${line} | awk '{print $1}')

    # remove the ./ from file name
    SRC_ABSOLUTE_PATH=${SRC_ABSOLUTE_PATH:2}

    DEST_FOLDER=${SRC_ABSOLUTE_PATH%/*}

    if [ ! -d ${PATCH_DIR}/${DEST_FOLDER} ]; then
      mkdir -p ${PATCH_DIR}/${DEST_FOLDER}
    fi

    # copy only the .png files to destination folder
    mv -f ${SRC_ABSOLUTE_PATH} ${PATCH_DIR}/${DEST_FOLDER}

    CREATE_PATCH_DIR="0"

  done <"${PWD}/${IMG_FILES}"

  # Go to application root again
  cd ..
}

find_and_convert_files() {

  # this is default directory of app sepcific documents, if not found exit
  cd documents || exit 1

  touch ${MD_FILES}

  # prepare the files which needs to be genrated
  tree -if -P *.md -I templates --prune | grep .md >${MD_FILES}

  while IFS= read -r line; do
    ## abosolute path
    SRC_ABSOLUTE_PATH=$(echo ${line} | awk '{print $1}')

    # remove the ./ from file name
    SRC_ABSOLUTE_PATH=${SRC_ABSOLUTE_PATH:2}

    DEST_FOLDER=${SRC_ABSOLUTE_PATH%/*}

    if [ ! -d ${PATCH_DIR}/${DEST_FOLDER} ]; then
      mkdir -p ${PATCH_DIR}/${DEST_FOLDER}
    fi

    #this is used to check how deep style has to be applied
    FOLDER_DEPTH=$(echo ${SRC_ABSOLUTE_PATH} | awk '{len = split($0,array,"/"); print len ;}')

    STYLE_LOC=""
    for ((c = 1; c <= ${FOLDER_DEPTH} - 1; c++)); do
      STYLE_LOC=${STYLE_LOC}"../"
    done

    SRC_ABSOLUTE_PATH_NAME=$(echo ${SRC_ABSOLUTE_PATH} | awk '{print}')

    if [[ ${SRC_ABSOLUTE_PATH_NAME} == *release_notes.md ]]; then
      REL_NOTES="1"
    else
      REL_NOTES="0"
    fi

    DEST_ABOSOLUTE_PATH=${SRC_ABSOLUTE_PATH/%.md/.html}

    if [[ ${REL_NOTES} == "1" ]]; then
      #generate the html files with pandoc utility
      pandoc --css=${STYLE_LOC}style/styling.css -s release_notes/templates/01_header.md ${SRC_ABSOLUTE_PATH} release_notes/templates/02_installation_steps.md -o ${DEST_ABOSOLUTE_PATH}

      if [[ ${GENERATE_MD5SUM} == "1" ]]; then
        #replace the MD5SUM value with build jar.
        REL_VERSION=$(echo ${PATCH_DIR} | awk '{split($0,arr,"_"); print arr[3]}')
        #match only the required release_version
        if [[ ${SRC_ABSOLUTE_PATH_NAME} == *${REL_VERSION}* ]]; then
          CHKSUM=$(echo $($MD5CMD $BUILD_FILE_LOCATION) | awk '{print $1}')
          sed -i -e "s/#MD5SUM#/${CHKSUM}/g" ${DEST_ABOSOLUTE_PATH}
        fi
      fi
    else
      #generate the html files with pandoc utility
      pandoc --css=${STYLE_LOC}style/styling.css -s ${SRC_ABSOLUTE_PATH} -o ${DEST_ABOSOLUTE_PATH}
    fi

    # replace .md extensions with .html
    sed -i -e "s/.md/.html/g" ${DEST_ABOSOLUTE_PATH}
    # copy only the .html files to destination folder
    mv -f ${DEST_ABOSOLUTE_PATH} ${PATCH_DIR}/${DEST_FOLDER}

    CREATE_PATCH_DIR="0"

  done <"${PWD}/${MD_FILES}"
}

setPlatform() {
  if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    PLATFORM="linux"
    MD5CMD="md5sum"
    CPPARAMS="-rf"
  elif [[ "$OSTYPE" == "darwin"* ]]; then
    # Mac OSX
    PLATFORM="mac"
    MD5CMD="md5"
    CPPARAMS="-Rf"
  else
    PLATFORM="unknown"
  fi
}

transfer_files() {
  # copy the style files to delta directory
  cp $CPPARAMS style ${PATCH_DIR}
  # accounts need additional folder so adding this as well.
  cp $CPPARAMS api_document/ ${PATCH_DIR}
  # work is done, so remove the files.
  rm ${MD_FILES} ${IMG_FILES}

  if [ ${CREATE_PATCH_DIR} == "0" ]; then
    if [ ${PLATFORM} == "linux" ]; then
      sshpass -p ${KUNJI_HOST_CONN_PWD} scp -r ${PWD}/"${PATCH_DIR}" ${KUNJI_HOST_CONN_STRING}"${APP_NAME}"
    elif [ ${PLATFORM} == "mac" ]; then
      scp -r ${PWD}/"${PATCH_DIR}" ${KUNJI_HOST_CONN_STRING}"${APP_NAME}"
    fi

    rm -rf ${PWD}/"${PATCH_DIR}"
    echo "SUCCESS: HTML generated and transfered."
  else
    echo "####################################"
    echo "ERROR: HTML not generated"
  fi
}

verify_locally() {
  # copy the style files to delta directory
  cp $CPPARAMS style ${PATCH_DIR}
  # work is done, so remove the files.
  rm ${MD_FILES} ${IMG_FILES}
  echo "SUCCESS: HTML generated and available at ${PWD}/${PATCH_DIR}"
}

#####################################################################################
# START
#####################################################################################
setPlatform
validation
generate_images_puml
find_and_convert_files
if [ ${ACTION} == "publish" ]; then
  transfer_files
elif [ ${ACTION} == "verify" ]; then
  verify_locally
else
  echo "!! ERROR: Unknown action : ${ACTION} "
fi

#####################################################################################
# END
#####################################################################################
