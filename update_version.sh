#!/bin/bash

# Đọc giá trị hiện tại của version từ gradle.properties
VERSION_CODE=$(grep -oP '(?<=android.injected.version.code=)\d+' gradle.properties)
VERSION_NAME=$(grep -oP '(?<=android.injected.version.name=)[\d\.]+(?=$)' gradle.properties)

# Tăng version code thêm 1
NEW_VERSION_CODE=$((VERSION_CODE + 1))

# Định dạng version name dựa trên version code
NEW_VERSION_NAME="1.0.${NEW_VERSION_CODE}"

# Cập nhật lại gradle.properties với version mới
sed -i '' "s/android.injected.version.code=.*/android.injected.version.code=${NEW_VERSION_CODE}/" gradle.properties
sed -i '' "s/android.injected.version.name=.*/android.injected.version.name=${NEW_VERSION_NAME}/" gradle.properties

echo "Updated version to ${NEW_VERSION_NAME} (${NEW_VERSION_CODE})"
