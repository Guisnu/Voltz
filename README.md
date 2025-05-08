# ⚡ Voltz - Plataforma de Investimento em Criptomoedas

[![Java](https://img.shields.io/badge/Java-23-blue)](https://www.oracle.com/java/)
[![OracleDB](https://img.shields.io/badge/OracleSQL-23-red)](https://www.oracle.com/database/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue)](https://maven.apache.org)

## 📋 Pré-requisitos

- **Oracle Database 23**
- **Java 23 JDK**
- **Maven 3.9+**

## 🚀 Configuração do Projeto

### 1. Configuração do Banco de Dados

1. Crie um usuário/schema no Oracle Database exclusivo para o projeto
2. Execute o script DDL disponível no repositório (`database/ddl.sql`) para criar todas as tabelas necessárias
3. Conceda as permissões necessárias ao usuário criado

### 2. Configuração da Conexão

Edite o arquivo `ConnectionService.java` com as credenciais do seu banco de dados:

```java
private static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCLCDB"; // Exemplo
private static final String USUARIO = "voltz"; // Seu usuário Oracle
private static final String SENHA = "sua_senha"; // Sua senha Oracle
```

**Formato da URL JDBC:**
```
jdbc:oracle:thin:@[HOST]:[PORT]:[SID]
```

### 3. Configuração do Ambiente

1. Clone o repositório:
   ```bash
   git clone https://github.com/guisnu/voltz.git
   cd voltz
   ```

2. Adicione o driver JDBC do Oracle como dependência no Maven ou coloque o arquivo `ojdbc11.jar` no classpath

## ▶️ Executando o Projeto

1. Compile o projeto com Maven:
   ```bash
   mvn clean install
   ```

2. Execute a aplicação:
   ```bash
   mvn exec:java -Dexec.mainClass="com.grupo.voltz.view.Main"
   ```

## 🛠 Funcionalidades Principais

- ✅ Cadastro e autenticação de usuários
- ✅ Gestão de carteiras de investimento
- ✅ Compra e venda de criptoativos
- ✅ Depósito e saque de fundos
- ✅ Transferência entre carteiras
- ✅ Histórico completo de transações
- ✅ Visualização de saldos e ativos
