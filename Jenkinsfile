pipeline {
    agent any
    
    tools {
        // 💡 PASTIKAN: Nama 'MAVEN' sesuai dengan yang ada di 
        // Manage Jenkins -> Tools -> Global Tool Configuration
        maven 'MAVEN'
        // Menggunakan Java bawaan dari Jenkins container (tidak perlu konfigurasi JDK terpisah)
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📥 Cloning repository...'
                checkout scm
            }
        }
        
        // ====== BUILD STAGE ======
        stage('Build All Services') {
            parallel {
                stage('Build Produk') {
                    steps {
                        echo '🔨 Building Produk Service...'
                        dir('produk') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Pelanggan') {
                    steps {
                        echo '🔨 Building Pelanggan Service...'
                        dir('pelanggan') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Order') {
                    steps {
                        echo '🔨 Building Order Service...'
                        dir('order') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }
        
        // ====== TEST STAGE ======
        stage('Test All Services') {
            parallel {
                stage('Test Pelanggan') {
                    steps {
                        echo '🧪 Testing Pelanggan Service...'
                        dir('pelanggan') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Test Produk') {
                    steps {
                        echo '🧪 Testing Produk Service...'
                        dir('produk') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Test Order') {
                    steps {
                        echo '🧪 Testing Order Service...'
                        dir('order') {
                            sh 'mvn test'
                        }
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo '''
            ═══════════════════════════════════════════════════════
            ✅ BUILD & TEST SUCCESSFUL!
            ═══════════════════════════════════════════════════════
            
            All 3 microservices have been built and tested:
               ✅ Pelanggan Service
               ✅ Produk Service  
               ✅ Order Service
            
            📝 NOTE: Untuk deployment, jalankan docker-compose secara
               terpisah dari mesin lokal Anda:
               
               docker compose up -d
            ═══════════════════════════════════════════════════════
            '''
        }
        failure {
            echo '❌ BUILD OR TEST FAILED! Check the logs above for details.'
        }
        always {
            echo "🔄 Pipeline completed at: ${new Date().format('yyyy-MM-dd HH:mm:ss')}"
        }
    }
}