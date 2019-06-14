SET JAVA_HOME="C:\Program Files\Java\jdk-12.0.1\bin"
SET PATH=%JAVA_HOME%;%PATH%
cd C:\Users\oscar\Documents\NetBeansProjects\copia\Compilador\src\analizador
java -jar C:\Fuente\java-cup-11b.jar -parser Sintactico -symbols Sym sintactico.cup
pause