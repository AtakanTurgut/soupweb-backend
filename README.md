# SoupWeb
SoupWeb, [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app) ile oluşturuşmuş bir [Next.js](https://nextjs.org/) projesidir.
Aynı zamanda back-end olarak [Springboot](https://start.spring.io/) kullanmaktadır.
Veri tabanı olaradak da [MySQL](https://dev.mysql.com/downloads/installer/) kullanılmaktadır.
<br>
`src\main\java\com\project\soupweb` dizininden backend kodlarına ulaşabilirsiniz.
<br>

## Next.js Projesi Oluşturma
![](/pictures/nextjs_create.PNG) 
Versiyona göre değişiklik gösterebilir!

## Projeyi Başlatırken

İlk olarak, geliştirme sunucusunu çalıştırın:

```bash
npm run dev
# ya da
yarn dev
```

Geliştirme sunucusu, tarayıcınızda [http://localhost:3000](http://localhost:3000) adresinde çalışır.
`pages/index.js` sayfasını düzenleyerek geliştirme işlemine başlayabilirsiniz. Bu sayfa, sizin değişikliklerinizin otomatik ve run-time olarak değişir.

Arka uç birim Springboot:

[Springboot](https://start.spring.io/) projesi, tarayıcınızda [http://localhost:8080](http://localhost:8080) adresinde çalışır.
Springboot proje özelliklerini görebilmek için `target/classes/application.properties` dizininden `application.properties` dosyasına göz atabilirsiniz.

Veri tabanına entegrasyonu başarılı bir şekilde sağlayabilmek için `application.properties` dosyası önemlidir.
[MySQL](https://dev.mysql.com/downloads/installer/), yerel adres olarak [mysql://localhost:3306/{{databaseName}}](http://localhost:3306/) adresini kullanır. MySQL'in Springboot içerisinde düzgün bir şekilde tanımlanabilmesi için oluşturulacak veri tabanı adı ile birlikte `application.properties` dosyasına veri tabanı username'i ve passwordu ile tanımlanmalıdır. 

`{{databaseName}}` sizin istediğiniz isimde bir veri tabanını temsil eder ve Springboot projesini başlatmadan önce aşağı kısımda belirtildiği gibi kendi MySQL Workbench'imizde {{databaseName}} isimli bir veri tabanı oluşturulmalıdır. 

```
spring.datasource.url=jdbc:mysql://localhost:3306/soupweb_database
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto = update
spring.servlet.multipart.enabled=true
```
## Docker İçin Yapılması Gerekenler
```
docker pull sudkostik/soupweb-image:latest
```

Docker ile sudkostik/soupweb-image adındaki latest sürümünü kullanarak soupweb projesini kurmak için aşağıdaki adımları takip edebilirsiniz:

Bilgisayarınıza Docker'ı kurun. Docker Desktop veya Docker Engine kullanabilirsiniz. Docker web sitesi üzerinden indirme sayfasına gidin ve işletim sisteminize uygun sürümü indirin ve kurulumu tamamlayın.

Docker CLI (Command Line Interface) kullanarak sudkostik/soupweb-image Docker imajını çekin. Aşağıdaki komutu çalıştırın:

arduino
Copy code
docker pull sudkostik/soupweb-image:latest
Docker imajını çalıştırmak için aşağıdaki komutu kullanın:

arduino
Copy code
docker run -p 8080:8080 --name soupweb-container -d sudkostik/soupweb-image:latest
Bu komut, 8080 portunu yerel makinenizdeki 8080 porta yönlendirerek SoupWeb uygulamasını çalıştıran bir Docker konteyneri oluşturacaktır.

Tarayıcınızı açın ve http://localhost:8080/ adresini ziyaret ederek SoupWeb uygulamasına erişin.

Projeyi sonlandırmak ve Docker konteynerini durdurmak için aşağıdaki komutu kullanın:

arduino
Copy code
docker stop soupweb-container
Bu adımları takip ederek sudkostik/soupweb-image Docker imajıyla soupweb projesini kolayca kurabilir ve çalıştırabilirsiniz.


## Gereksinimler Rest API Testi
![](/pictures/SoupWebFullApiTest.PNG) 