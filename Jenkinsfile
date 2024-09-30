pipeline {
    agent any

    environment {
        PATH = "/opt/homebrew/bin:$PATH"
        APP_CENTER_API_TOKEN = credentials('APP_CENTER_API_TOKEN')
        ANDROID_KEYSTORE = credentials('ANDROID_KEYSTORE')  // Keystore file
        KEYSTORE_PASSWORD = credentials('KEYSTORE_PASSWORD')  // Mật khẩu của keystore
        KEY_ALIAS = credentials('KEY_ALIAS')  // Alias của key
        KEY_PASSWORD = credentials('KEY_PASSWORD')  // Mật khẩu của key alias
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
                sh '''
                    ./gradlew clean assembleStagingDebug \
                    -Pandroid.injected.signing.store.file=$WORKSPACE/keystore/Android.jks \
                    -Pandroid.injected.signing.store.password=$KEYSTORE_PASSWORD \
                    -Pandroid.injected.signing.key.alias=$KEY_ALIAS \
                    -Pandroid.injected.signing.key.password=$KEY_PASSWORD
                '''
            }
        }

        stage('Verify APK') {
            steps {
                sh 'ls -la ./app/build/outputs/apk/staging/debug/'
            }
        }

        stage('Upload to AppCenter') {
            steps {
                sh '''
                    appcenter distribute release \
                    --app huy.mobcontact-gmail.com/FSquare-Android-Application \
                    --group "Testers" \
                    --file ./app/build/outputs/apk/staging/debug/30.09.14.25-stagingDebug-1.1.0.apk \
                    --token $APP_CENTER_API_TOKEN
                '''
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
