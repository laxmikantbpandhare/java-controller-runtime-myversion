package io.fabric8.memcached.operator.controller_runtime.pkg;

public class Request {

    private String name;
    private String namespace;


    public Request(String name) {
        this(null, name);
    }


    public Request(String namespace, String name) {
        this.name = name;
        this.namespace = namespace;
    }


    /**
     * Gets namespace.
     *
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets namespace.
     *
     * @param namespace the namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

}
