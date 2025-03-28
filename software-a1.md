## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1
**Prof. Delano M. Beder (UFSCar)**

Algumas sugestões incluídas por André T. Endo.

**Dicas sobre a instalação de softwares**


- - -
#### JDK
- - -

1. Instalar o Java Development Kit
2. Há duas opções atualmente, da Oracle e OpenJDK

    ```
    https://www.oracle.com/technetwork/java/javase/downloads/index.html

    https://openjdk.java.net/
    ```

    2.1. Existe a ferramenta SDKMAN que auxilia no gerenciamento de várias versões do SDK do Java. Ele também configura automaticamente a variável de ambiente JAVA_HOME. Veja como instalar em https://sdkman.io/install e seu uso básico em https://sdkman.io/usage. Instalado, o seguinte comando pode ser usado para instalar a última versão do Java: 

    ```sh
    sdk install java
    ```

3. Para testar se funcionou, executar em um terminal

    ```sh
    java -version
    javac -version
    ```


- - -
#### Eclipse
- - -

1. Baixar a versão *Eclipse IDE for Enterprise Java Developers* em  https://www.eclipse.org/downloads/packages/
    - O Eclipse Installer também pode ser usado (instala o JRE junto a IDE)
2. Descompactar o conteúdo em alguma pasta (`<instalação eclipse>`)

    ```sh
    tar -xvzf eclipse-inst-jre-linux64.tar.gz
    ```

3. Para iniciar o eclipse, clique no arquivo executável (**eclipse.exe**, **eclipse**, etc) presente no diretório `<instalação eclipse>` 


- - -
#### Intellij IDEA
- - -

Faça o download em https://www.jetbrains.com/pt-br/idea/download; a versão community é suficiente mas a versão Pro pode ser obtida pedindo uma licença acadêmica. 

- - -
#### Apache Maven
- - -

Instalação Windows: https://dicasdejava.com.br/como-instalar-o-maven-no-windows/

Instalação Ubuntu: https://www.hostinger.com.br/tutoriais/install-maven-ubuntu/. A instalação usado o comando apt-get é mais simples. 



1. Baixar o Maven de ```https://maven.apache.org```
2. O Maven precisa estar no ```PATH```, e precisa conhecer a variável ```JAVA_HOME```
- Rodar os seguintes comandos no terminal (substituir pelos caminhos corretos):

    ```sh
    export JAVA_HOME=$HOME/Programs/jdk1.8.0_212/
    export PATH=$HOME/Programs/apache-maven-3.6.1/bin:$PATH
    ```

- Esses comandos precisam ser executados sempre que um novo terminal for iniciado. É também possível configurar permanentemente (consulte a documentação do Sistema Operacional para como fazer isso).
- No Linux/Mac, uma boa prática é criar um script temporário para evitar de mexer nas configurações globais
- Criar um arquivo useMaven.sh, com permissão de execução, com o conteúdo acima
- Antes de iniciar o uso do Maven, executar

    ```sh
    source useMaven.sh
    ```

3. Para testar se funcionou, executar:

    ```sh
    mvn -version
    ```

4. Para compilar e gerar um arquivo .war, utilizar o seguinte comando

    ```sh
    mvn clean package
    ```

5. Criar um projeto que usa uma dependência externa. Use o comando a seguir:

```sh
mvn archetype:generate -DgroupId=com.example -DartifactId=proj1 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

Abra o projeto no Eclipse ou outra IDE. Modificar o arquivo pom.xml para compilar o projeto usando Java 21 e adicionar a dependência para usar a biblioteca commons-csv. Usar o site https://mvnrepository.com/ para buscar bibliotecas de interesse.  

```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.5</version>
</dependency>
```

Após as alterações, o arquivo pom.xml terá a estrutura a seguir. 

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>proj1</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>proj1</name>
  <url>http://maven.apache.org</url>
  
  <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
  
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-csv</artifactId>
      <version>1.5</version>
     </dependency>
  </dependencies>
</project>
```

Execute o comando a seguir para instalar as dependências. 

```sh
mvn clean install
```

Crie o arquivo clients.csv na raiz do projeto com os seguintes dados:

```csv
Name,Age,Email
Alice Johnson,28,alice.johnson@example.com
Bob Smith,35,bob.smith@example.com
Charlie Davis,42,charlie.davis@example.com
Diana Evans,30,diana.evans@example.com
Ethan Carter,25,ethan.carter@example.com
Fiona Green,38,fiona.green@example.com
George Harris,45,george.harris@example.com
Hannah Lewis,32,hannah.lewis@example.com
Ian Martinez,29,ian.martinez@example.com
Julia Nelson,40,julia.nelson@example.com
```

Modifique uma classe Java que contém o método main() para conter o seguinte código que usa a biblioteca. Execute e resolva os erros. 

```java
Reader reader = Files.newBufferedReader(Paths.get("./clients.csv"));

CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

Iterable<CSVRecord> csvRecords = csvParser.getRecords();
for (var record : csvRecords) {
    System.out.println(record.get("name"));
}
csvParser.close();
```

6. Opcionalmente, é possível criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento. Também evita a necessidade de executar o comando ```source``` a cada novo terminal.

- Abrir um terminal dentro da pasta do projeto e executar o seguinte comando

    ```sh
    mvn -N io.takari:maven:wrapper
    ```

- Depois disso, será criado um arquivo executável ```mvnw```. Ao invés de executar ```mvn```, basta executar esse ```mvnw```.
- Esse arquivo ```mvnw``` e demais criados devem ser compartilhados com a equipe no repositório de controle de versões



- - -
#### Apache Tomcat
- - -

1. Baixar o arquivo zip de ```http://tomcat.apache.org/```

versão core

2. Descompactar em uma pasta sem espaços ou acentos (diretório `<instalação tomcat>`)

3. Explorar a estrutura de diretórios (diretório `<instalação tomcat>`)

4. Abrir o arquivo `conf/server.xml` (configuração do servidor tomcat)

5. Abrir o arquivo `conf/tomcat-users.xml` (configuração dos usuários)

6. Rodar o tomcat
   
   6.1. No windows
  
      - Abrir o Windows PowerShell
      - Rodar `$env:JAVA_HOME="C:\<caminho_para_java>\jreXXX"`
      - Executar `startup.bat` (ou `catalina.bat run`) no diretório `<instalação tomcat>\bin`
   
   6.2. No Linux/Mac
   
      - Abrir um terminal
      
      - Rodar `export JAVA_HOME="/<caminho_para_java>/jreXXX"`
      
      - Executar `startup.sh` (ou `catalina.sh run`) no diretório `<instalação tomcat>\bin` 
         [pode ser necessário dar permissão – executar comando **chmod 755**]

7. Abrir em um navegador a URL: http://localhost:8080
   
   7.1. Observar que não tem acesso ao **manager**
   
   7.2. Modificar `conf/tomcat-users.xml`, adicionando a seguinte linha (obs: será importante na execução de nossos exemplos)

    ```xml
    <user username="admin" password="admin" roles="manager-gui, manager-script" />
    ```
   7.3. Parar tomcat e reiniciar, e tentar novamente

8. Acessar aplicações **manager** e **status**

9. Fim



- - -
#### Apache Derby
- - -

1. Baixar o servidor: `http://db.apache.org/derby/`

2. Descompactar o conteúdo em alguma pasta (`<instalação Derby>`)

3. Para iniciar/interromper o servidor, abrir o terminal e executar os seguintes comandos (substituir o caminho do `derby.system.home` por uma pasta onde serão criados os bancos de dados e a instalação do Derby onde foi descompactado seu conteúdo):

    ```sh
    java -Dderby.system.home=<pasta com bancos de dados> -jar <instalação Derby>/lib/derbyrun.jar server start

    java -Dderby.system.home=<pasta com bancos de dados> -jar <instalação Derby>/lib/derbyrun.jar server shutdown
    ```

- No exemplo a seguir, o servidor está instalado na pasta ```/usr/lib/jvm/db-derby-10.15.2.0-bin``` e irá buscar os bancos de dados na pasta ```/home/delano/DerbyDatabases```:

    ```sh
    java -Dderby.system.home=/home/delano/DerbyDatabases -jar /usr/lib/jvm/db-derby-10.15.2.0-bin/lib/derbyrun.jar server start
    ```

4. Para criar um banco de dados, executar em um terminal (não é necessário que o servidor esteja rodando):

    ```sh
    java -Dderby.system.home=<caminho bancos de dados> -Dij.protocol=jdbc:derby: -jar <instalação Derby>/lib/derbyrun.jar ij
    ```

5. Uma vez executando o comando `ij`, executar o seguinte comando (obs: `meubanco` é o nome do banco de dados. `create=true` indica que o banco será criado, caso não exista):

    ```
    CONNECT 'meubanco;create=true';
    ```

- Caso queira criar o banco com um usuário e senha:

    ```
    CONNECT 'meubanco;create=true;user=usuario;password=senha';
    ```

- Após esse comando, uma pasta chamada "meubanco" será criada no diretório especificado em `derby.system.home`

- Caso não queira criar um novo banco, apenas conectar, executar o comando:

    ```sql
    CONNECT 'meubanco';
    ```

6. Uma vez conectado, é possível executar comandos SQL:

    ```sql
    CREATE TABLE Teste (codigo int, nome varchar(100));
    ````

7. Para sair do `ij`, executar:

    ```sql
    exit;
    ```

8. Outros comandos para serem testados na interface `ij`: 

```sql
CONNECT 'jdbc:derby:meubanco;user=usuario;password=senha';

INSERT INTO Teste (codigo, nome) VALUES (1, 'Delano');
INSERT INTO Teste (codigo, nome) VALUES (2, 'Alan');
INSERT INTO Teste (codigo, nome) VALUES (3, 'Andre');

select * from Teste;

CREATE TABLE Event (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, Name VARCHAR(255) NOT NULL, EventDate DATE NOT NULL);


INSERT INTO Event (Name, EventDate) VALUES ('Secomp', '2025-05-10');
INSERT INTO Event (Name, EventDate) VALUES ('Churrasco da turma', '2025-06-15');
INSERT INTO Event (Name, EventDate) VALUES ('Happyhour da Galera', '2025-07-20');
```


- - -
#### Spring Tools Suite
- - -

1. Baixar a versão *Spring Tools 4 for Eclipse* em https://spring.io/tools/
2. Descompactar o conteúdo em alguma pasta (`<instalação sts>`)
3. Para iniciar o sts, clique no arquivo executável (**SpringToolSuite4.exe**, **SpringToolSuite4**, etc) presente no diretório `<instalação sts>` 
