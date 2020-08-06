#!/bin/sh -

pip install -r mkdocs-requirements.txt

rm -rf docs
rm -rf api/docs/packages

mkdir docs

./gradlew dokka

mkdocs build
