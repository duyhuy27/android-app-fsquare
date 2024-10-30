pipeline {
    agent any

    environment {
        PATH = "/opt/homebrew/bin:$PATH"
        APP_CENTER_API_TOKEN = credentials('APP_CENTER_API_TOKEN')
        ANDROID_KEYSTORE = credentials('ANDROID_KEYSTORE')
        KEYSTORE_PASSWORD = credentials('KEYSTORE_PASSWORD')
        KEY_ALIAS = credentials('KEY_ALIAS')
        KEY_PASSWORD = credentials('KEY_PASSWORD')
        TELEGRAM_TOKEN = credentials('TELEGRAM_TOKEN')
        TELEGRAM_CHAT_ID = credentials('TELEGRAM_CHAT_ID')
    }

    stages {
//         stage('Update Version') {
//             steps {
//                 script {
//                     // Lấy ngày giờ hiện tại để sử dụng trong version name
//                     def currentDate = new Date()
//                     def month = String.format("%02d", currentDate.getMonth() + 1) // Tháng (0-11)
//                     def day = String.format("%02d", currentDate.getDate()) // Ngày (1-31)
//                     def hour = String.format("%02d", currentDate.getHours()) // Giờ (0-23)
//                     def minute = String.format("%02d", currentDate.getMinutes()) // Phút (0-59)
//
//                     // Đặt version name theo định dạng MMDDHHmm
//                     def versionName = "${month}${day}${hour}${minute}"
//
//                     // Cập nhật file build.gradle với versionName mới
//                     sh """
//                         sed -i "s/versionName .*/versionName '${versionName}'/" app/build.gradle
//                     """
//                 }
//             }
//         }

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
                    def apkPath = sh(script: 'find ./app/build/outputs/apk/dev/debug/ -name "*.apk"', returnStdout: true).trim()
                    if (apkPath) {
                        echo "APK path: ${apkPath}"
                        sh """
                            appcenter distribute release \
                            --app huy.mobcontact-gmail.com/FSquare-Android-Application \
                            --group "Testers" \
                            --file ${apkPath} \
                            --token $APP_CENTER_API_TOKEN
                        """
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
            // Gửi tin nhắn Telegram
            sh """
                curl -s -X POST https://api.telegram.org/bot\$TELEGRAM_TOKEN/sendMessage \
                -d chat_id=\$TELEGRAM_CHAT_ID \
                -d text='🎉 **FSquare Build Notification** 🎉\n\nChúng tôi vui mừng thông báo rằng bản build mới của ứng dụng FSquare đã thành công! \n\n✅ **Phiên bản mới nhất đã được tải lên** và sẵn sàng để bạn trải nghiệm.\n\n🔗 **Tải ngay APK tại đây:** [FSquare - Download](https://install.appcenter.ms/users/huy.mobcontact-gmail.com/apps/fsquare-android-application/distribution_groups/testers)\n\nCảm ơn bạn đã đồng hành cùng chúng tôi! Hãy cùng khám phá những tính năng mới và cải tiến trong ứng dụng FSquare.' \
                -d parse_mode='Markdown'
            """
        }
        failure {
            echo 'Build or upload to AppCenter failed.'
            // Gửi tin nhắn Telegram khi build thất bại
            sh """
                curl -s -X POST https://api.telegram.org/bot\$TELEGRAM_TOKEN/sendMessage \
                -d chat_id=\$TELEGRAM_CHAT_ID \
                -d text='Build failed. Please check the logs.'
            """
        }
    }
}
