#!/bin/bash

REPO="theofidry/SoftwareQuality-ECEProject"

if [ "$TRAVIS_REPO_SLUG" == ${REPO} ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Publishing javadoc...\n"

  cp -R build $HOME/build

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/${REPO} gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc
  git rm -rf ./build
  cp -Rf $HOME/build ./build
  git add -f .
  git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc to gh-pages.\n"

fi
