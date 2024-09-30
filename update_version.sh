#!/bin/bash

# Tăng version.build lên 1
if [ -f "version.properties" ]; then
  versionBuild=$(grep "version.build" version.properties | cut -d'=' -f2)
  newVersionBuild=$((versionBuild + 1))

  sed -i "" "s/version.build=$versionBuild/version.build=$newVersionBuild/" version.properties
  echo "Updated version.build to $newVersionBuild"
else
  echo "version.properties not found!"
  exit 1
fi
