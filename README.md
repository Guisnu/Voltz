# ⚡ Voltz - Crypto Investment Platform

[![Java](https://img.shields.io/badge/Java-23-blue)](https://www.oracle.com/java/)
[![OracleDB](https://img.shields.io/badge/OracleSQL-23c-red)](https://www.oracle.com/database/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue)](https://maven.apache.org)

Plataforma de simulação de investimentos em criptomoedas desenvolvida com:

- **Backend**: Oracle Database 23c
- **Linguagem**: Java 23 (LTS 2024)
- **Build**: Maven 3.9+

Como Executar

### Pré-requisitos
- Oracle Database 23c
- Java 23 JDK
- Maven 3.9+

### Configuração
1. Clone o repositório:
   ```bash
   git clone https://github.com/guisnu/voltz.git
   cd voltz
   ```

2. Configure a conexão no arquivo:
   `src/main/resources/application.properties`

3. Execute com Maven:
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="com.grupo.voltz.view.Main"
   ```

## 🛠 Principais Funcionalidades
- Gestão completa de contas de investimento
- Operações com múltiplas carteiras
- Compra/venda de criptoativos
- Relatórios e histórico de transações
