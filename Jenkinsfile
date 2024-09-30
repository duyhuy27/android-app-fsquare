pipeline {
    agent any

    environment {
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
                sh '''
                    appcenter distribute release \
                    --app huy.mobcontact-gmail.com/FSquare-Android-Application \
                    --group "Testers" \
                    --file ./app/build/outputs/apk/release/app-release.apk \
                    --token $APP_CENTER_API_TOKEN
                '''
            }
        }
    }

    post {
        always {
            node { cleanWs() }
        }
        success {
            echo 'Build and upload to AppCenter succeeded!'
        }
        failure {
            echo 'Build or upload to AppCenter failed.'
        }
    }
}
