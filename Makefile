all:
	javac -d out -sourcepath src -classpath sound src/main.java
	java -cp out:sound main