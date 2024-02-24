# RBAC SaaS API

Spring Boot-based blogging application API providing a platform for efficient content creation and management. Implemented Pagination and Validation. Authentication and Authorization of users are implemented using Spring Security JWT tokens.

**Language:** Java 8  
**Framework:** Spring Boot, Spring Data JPA, Spring Security - Authentication and Authorization using JWT, RBAC.  
**Database:** MySQL  
**Deployment:** AWS Elastic Beanstalk, AWS RDS.  
**Link:** [Swagger UI](http://blogapplication-env.eba-2npj9ep6.ap-south-1.elasticbeanstalk.com/swagger-ui/index.html)

**Entities:** User, Post, Comment, Categories, Role  
**Relationships:**
- User to Post (OneToMany)
- Post to Comment (OneToMany)
- User to Comment (OneToMany)
- Category to Post (ManyToOne)
- User to Role (ManyToMany)

![Schema](https://github.com/Siddhanttimeline/backup_BLOG/assets/86899184/c040dab8-36c4-4566-92eb-383acf400a4e)
