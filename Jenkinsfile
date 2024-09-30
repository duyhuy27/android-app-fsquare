pipeline {
    agent any

    environment {
        PATH = "/opt/homebrew/bin:$PATH"
        APP_CENTER_API_TOKEN = credentials('APP_CENTER_API_TOKEN')
        ANDROID_KEYSTORE = credentials('ANDROID_KEYSTORE')
        KEYSTORE_PASSWORD = credentials('KEYSTORE_PASSWORD')
        KEY_ALIAS = credentials('KEY_ALIAS')
        KEY_PASSWORD = credentials('KEY_PASSWORD')
    }

    stages {
        stage('Create local.properties') {
            steps {
                sh '''
                    echo "sdk.dir=/Users/huynguyen/Library/Android/sdk" > local.properties
                    echo "keyAlias=$KEY_ALIAS" >> local.properties
                    echo "keyPassword=$KEY_PASSWORD" >> local.properties
                    echo "keystore=$ANDROID_KEYSTORE" >> local.properties
                    echo "keystorePassword=$KEYSTORE_PASSWORD" >> local.properties
                '''
            }
        }

        stage('Build') {
            steps {
                sh '''
                    mkdir -p $WORKSPACE/keystore
                    cp $ANDROID_KEYSTORE $WORKSPACE/keystore/Android.jks
                '''
                // Build APK trong dev/debug
                sh '''
                    ./gradlew clean assembleDevDebug \
                    -Pandroid.injected.signing.store.file=$WORKSPACE/keystore/Android.jks \
                    -Pandroid.injected.signing.store.password=$KEYSTORE_PASSWORD \
                    -Pandroid.injected.signing.key.alias=$KEY_ALIAS \
                    -Pandroid.injected.signing.key.password=$KEY_PASSWORD
                '''
            }
        }

        stage('Verify APK') {
            steps {
                sh 'ls -la ./app/build/outputs/apk/dev/debug/'
            }
        }

        stage('Upload to AppCenter') {
            steps {
                script {
                    // Tìm file APK trong thư mục dev/debug
                    def apkPath = sh(script: 'find ./app/build/outputs/apk/dev/debug/ -name "*.apk"', returnStdout: true).trim()

                    if (apkPath) {
                        echo "APK path: ${apkPath}"  // Kiểm tra đường dẫn APK
                        def releaseOutput = sh(script: """
                            appcenter distribute release \
                            --app huy.mobcontact-gmail.com/FSquare-Android-Application \
                            --group "Testers" \
                            --file ${apkPath} \
                            --token $APP_CENTER_API_TOKEN
                        """, returnStdout: true).trim()

                        // Kiểm tra xem phát hành thành công và hiển thị thông báo
                        if (releaseOutput.contains('Release')) {
                            echo "Build and upload to AppCenter succeeded!"
                            echo "APK released successfully. You can share the following link to download the APK:"
                            echo "https://install.appcenter.ms/users/huy.mobcontact-gmail.com/apps/FSquare-Android-Application/releases/latest"
                        } else {
                            error "Failed to upload APK to AppCenter."
                        }
                    } else {
                        error "APK not found!"
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build and upload to AppCenter succeeded!'
        }
        failure {
            echo 'Build or upload to AppCenter failed.'
        }
    }
}
