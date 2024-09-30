stage('Upload to AppCenter') {
    steps {
        script {
            // Tìm file APK động dựa vào thư mục và phần đuôi file APK
            def apkPath = sh(script: 'find ./app/build/outputs/apk/staging/debug/ -name "*.apk"', returnStdout: true).trim()

            // Kiểm tra xem APK có tồn tại không
            if (apkPath) {
                sh '''
                    appcenter distribute release \
                    --app huy.mobcontact-gmail.com/FSquare-Android-Application \
                    --group "Testers" \
                    --file ${apkPath} \
                    --token $APP_CENTER_API_TOKEN
                '''
            } else {
                error "APK not found!"
            }
        }
    }
}
