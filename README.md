maven-kar-archive
=================

Example how to create karaf kar archives using maven.  

Deployment
----------
* Copy the generated .kar file into [apache-karaf-x.x.x]/deploy folder

CAUTION
-------
Deployment might result in Stacktrace:

    java.lang.VerifyError: Expecting a stackmap frame at branch target 77 in method ...

**Background:**
* http://chrononsystems.com/blog/java-7-design-flaw-leads-to-huge-backward-step-for-the-jvm
* https://issues.apache.org/jira/browse/FELIX-3568  

**Solution:**  
* add "*export set JAVA_OPTS="-XX:-UseSplitVerifier"*" to your .bashrc
* alternative - http://mail-archives.apache.org/mod_mbox/felix-users/201308.mbox/%3C1148955CCC8F2E47AFCE7A1A54C4AB3F01EDB940@sol-rz-ex04.solon.loc%3E
