pipeline {
    agent any

    environment {
        // Dùng Jenkins Credentials để lấy API Token cho AppCenter
        APP_CENTER_API_TOKEN = credentials('APP_CENTER_API_TOKEN')
        ANDROID_KEYSTORE = credentials('ANDROID_KEYSTORE')  // Keystore file
        KEYSTORE_PASSWORD = credentials('KEYSTORE_PASSWORD')  // Mật khẩu của keystore
        KEY_ALIAS = credentials('KEY_ALIAS')  // Alias của key
        KEY_PASSWORD = credentials('KEY_PASSWORD')  // Mật khẩu của key a
    }

      stage('Build') {
             steps {
                    // Lưu file keystore tạm thời để sử dụng trong quá trình build
                    sh '''
                        mkdir -p $WORKSPACE/keystore
                        cp $ANDROID_KEYSTORE $WORKSPACE/keystore/Android.jks
                    '''

                    // Build APK với Gradle và sử dụng keystore để ký
                    sh '''
                        ./gradlew clean assembleRelease \
                        -Pandroid.injected.signing.store.file=$WORKSPACE/keystore/Android.jks \
                        -Pandroid.injected.signing.store.password=$KEYSTORE_PASSWORD \
                        -Pandroid.injected.signing.key.alias=$KEY_ALIAS \
                        -Pandroid.injected.signing.key.password=$KEY_PASSWORD
                    '''
                }
            }

        stage('Upload to AppCenter') {
            steps {
                // Upload APK đã build lên AppCenter với AppCenter CLI
                sh '''
                    appcenter distribute release \
                    --app huy.mobcontact-gmail.com/FSquare-Android-Application
                    --group "Testers" \
                    --file ./app/build/outputs/apk/release/app-release.apk
                    --token $APP_CENTER_API_TOKEN
                '''
            }
        }
    }

    post {
        always {
            cleanWs()  // Dọn dẹp workspace sau khi build
        }
        success {
            echo 'Build and upload to AppCenter succeeded!'
        }
        failure {
            echo 'Build or upload to AppCenter failed.'
        }
    }
}
