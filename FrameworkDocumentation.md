# Concepts #

## Component definitions ##

A component _definition_ is an XML fragment (actually an XML element) that contains the configuration of a particular component. A definition is registered as an OSGi service with class `Definition` and with a property identifying the _component type_, which is simply the QName of the root element of the XML fragment.

Component definitions are conceptually similar to Spring bean definitions with custom namespaces. On the other hand, there is no equivalent of Spring's `<bean>` tag in the SiSME framework.

## Components ##

_Components_ are created at runtime from component definitions. A component has a unique ID (called _component ID_), which is the OSGi `service.id` of the corresponding `Definition` service. Since service IDs are not persistent across restarts of the OSGi framework, this ID can only be used to identify a component at runtime. Therefore, a name can also be assigned to the component. This _component name_ is a QName that can be used to refer to a particular component, e.g. in another component definition.

A SiSME component is somewhat similar to a Spring bean. However, there are some important differences. At runtime, a Spring bean is always represented by a unique instance of a particular Java class. This is not the case for a SiSME component. In addition, because the SiSME framework leverages the dynamic nature of OSGi, the lifecycle of a component is different from the lifecycle of a Spring bean.

## Facets ##

A component _facet_ is an interface that provides access to specific component methods. A facet is defined by a particular Java interface, an instance of which is registered as an OSGi service at runtime. Note that this means that there is no 1-to-1 relationship between components and OSGi services. In addition, since an OSGi service can expose several interfaces, there is also no 1-to-1 relationship between facets and OSGi services; a single OSGi service can represent multiple facets of a component.

## Definition processors ##

A _definition processor_ is an OSGi service of type `DefinitionProcessor` that is able to process component definitions for a particular component type and to create a set of facets for the component.

## Dependencies ##

A component can have _dependencies_ on other components. A dependency always refers to a particular facet of another component. The target component is selected in one of several ways:
  * By component name. This is typically used when the target of the dependency is declared explicitly in the component definition.
  * By component ID. Since the ID of a component may change, this is only used for embedded definitions (see below) and usage of component IDs in component definitions is not allowed.
  * Using a filter expression. In this case, the target component is selected dynamically using some criteria. This can also be used to create dependencies on OSGi services which are not registered by SiSME components.
A component will be instantiated (i.e. its facets registered as OSGi services) when all dependencies are satisfied (more precisely, if each dependency is satisfied exactly once). If a dependency is no longer satisfied (because the target component facet has been unregistered), then the component will be automatically destroyed.

## Embedded definitions ##

TODO

## Facet factories ##

TODO