#Coupon Management System
the core of the system using Spring Boot framework, Hibernate, JPA


##Instructions

#Eclipse
1.Open Eclipse and select File > Import.
2.In the import wizard, choose Maven > Existing Maven Projects, then click Next.
3.Select the java-maven-starter-project as the project root directory.
4.Click Finish to complete the import.
5.Select Project > Properties . In Java Build Path, ensure that under the Libraries tab, Modulepath is set to JRE System Library (JavaSE-11). In Java Compiler, ensure that the Use compliance from execution environment 'JavaSE-11' on the 'Java Build Path' checkbox is selected.
6.Right-click the project in the Project Explorer or Package Explorer and choose Run As > Maven Build.... In the Edit Configuration dialog, create a new configuration with name unpack. In the Goals field, enter dependency:unpack. Click Run to run the goal. This will unpack the native libraries into $USER_HOME/.arcgis.
7.Create a new run configuration with name run. In the Goals field, enter compile exec:java. Click Run to run the goal. The app should compile and launch the JavaFX window.
