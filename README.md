[![Build Status][travis-badge]][travis-badge-url]


Java Reflect Proxy And cglib Examples
======================================
cglib examples based on [cglib Missing Manual](http://mydailyjava.blogspot.no/2013/11/cglib-missing-manual.html) a
an [cglib Tutorial](https://github.com/cglib/cglib/wiki/Tutorial)

### cglib Utils

#### Enhancer
`Enhancer` allows the creation of Java proxies for non-interface types. It's 
similar to Java's Proxy. The Enhancer dynamically creates a subclass of a given
 type and intercepts all method calls except `final` methods. It cannot
 create proxies for `final` classes.
 
The class names are generated randomly by cglib in the same package
as the enhanced class (and therefore be able to override package-private methods). 


#### Immutable Bean
`ImmutableBean` is an utility of cglib which creates immutable beans. Examples can be found in `ImmutableBean.java`. 

#### Bean Generator
`BeanGenerator` is an utility of cglib which creates new beans at run time. 
Examples can be found in ` BeanGeneratorTest.java`. 

#### Bean Copier
`BeanCopier` is an utility of cglib which copies beans by their property values. 
Examples can be found in ` BeanCopierTest.java`. 

### Build
Execute the following command from the parent directory:
```
mvn clean install
```


[travis-badge]: https://travis-ci.org/indrabasak/cglib-examples.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/cglib-examples/