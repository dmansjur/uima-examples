Chapter 5. CAS Utilities
============================================

uimaFIT facilitates working with the CAS and JCas by offering various convenient methods for accessing and navigating annotations and feature structures. Additionally, the the convenience methods for JCas access are fully type-safe and return the JCas type or a collection of the JCas type which you wanted to access.

### 5.1. Access methods

uimaFIT supports the following convenience methods for accessing CAS and JCas structures. All methods respect the UIMA index definitions and return annotations or feature structures in the order defined by the indexes. Unless the default UIMA index for annotations has been overwritten, annotations are returned sorted by begin (increasing) and end (decreasing).

select(cas, type) - fetch all annotations of the given type from the CAS/JCas. Variants of this method also exist to fetch annotations from a FSList or FSArray.

selectAll(cas) - fetch all annotations from the CAS or fetch all feature structures from the JCas.

selectBetween(type, annotation1, annotation2)* - fetch all annotations between the given two annotations.

selectCovered(type, annotation)* - fetch all annotations covered by the given annotation. If this operation is used intensively, indexCovered(...) should be used to pre-calculate annotation covering information.

selectCovering(type, annotation)* - fetch all annotations covering the given annotation. If this operation is used intensively, indexCovering(...) should be used to pre-calculate annotation covering information.

selectByIndex(cas, type, n) - fetch the n-th feature structure of the given type.

selectSingle(cas, type) - fetch the single feature structure of the given type. An exception is thrown if there is not exactly one feature structure of the type.

selectSingleRelative(type, annotation, n)* - fetch a single annotation relative to the given annotation. A positive n fetches the n-th annotation right of the specified annotation, while the a negative n fetches to the left.

selectPreceding(type, annotation, n)* - fetch the n annotations preceding the given annotation. If there are less then n preceding annotations, all preceding annotations are returned.

selectFollowing(type, annotation, n)* - fetch the n annotations following the given annotation. If there are less then n following annotations, all following annotations are returned.

Note
For historical reasons, the method marked with * also exist in a version that accepts a CAS/JCas as the first argument. These may not work as expected when the annoation arguments provided to the method are from a different CAS/JCas/view. Also, for any method accepting two annotations, these should come from the same CAS/JCas/view. In future, the potentially problematic signatures may be deprecated, removed, or throw exeptions if these conditions are not met.

Note
You should expect the structures returned by these methods to be backed by the CAS/JCas contents. In particular, if you remove any feature structures from the CAS while iterating over these structures may cause failures. For this reason, you should also not hold on to these structures longer than necessary, as is the case for UIMA FSIterators as well.

Depending on whether one works with a CAS or JCas, the respective methods are available from the JCasUtil or CasUtil classes.

JCasUtil expect a JCas wrapper class for the type argument, e.g. select(jcas, Token.class) and return this type or a collection using this generic type. Any subtypes of the specified type are returned as well. CasUtil expects a UIMA Type instance. For conveniently getting these, CasUtil offers the methods getType(CAS, Class<?>) or getType(CAS, String) which fetch a type either by its JCas wrapper class or by its name.

Unless annotations are specifically required, e.g. because begin/end offsets are required, the JCasUtil methods can be used to access any feature structure inheriting from TOP, not only annotations. The CasUtil methods generally work only on annotations. Alternative methods ending in "FS" are provided for accessing arbitrary feature structures, e.g. selectFS.

```sh
Examples:

// CAS version
Type tokenType = CasUtil.getType(cas, "my.Token");
for (AnnotationFS token : CasUtil.select(cas, tokenType)) {
  ...
  }

// JCas version
for (Token token : JCasUtil.select(jcas, Token.class)) {
  ...
  }
```