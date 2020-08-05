#!/bin/sh -

pip install -r mkdocs-requirements.txt

rm -rf docs
rm -f api/index.md
rm -rf api/packages
cp README.md api/index.md

mkdir docs

./gradlew dokka

mkdocs build
