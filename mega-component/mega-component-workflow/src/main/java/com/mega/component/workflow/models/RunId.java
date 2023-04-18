package com.mega.component.workflow.models;

public class RunId extends Id
{
    /**
     * Generate a new, globally unique ID that has the given prefix.
     * E.g. <code>newRandomIdWithPrefix("test")</code> generates: <code>test-828119e0-cd47-45a1-b120-94d284ecb7b3</code>
     *
     * @param prefix ID's prefix
     * @return new ID
     */
    public static RunId newRandomIdWithPrefix(String prefix)
    {
        return new RunId(prefix + "-" + newRandomId());
    }

//    public static void main(String[] args)
//    {
//        System.out.println(newRandomIdWithPrefix("hey"));
//        System.out.println(newRandomIdWithPrefix("hey"));
//        System.out.println(newRandomIdWithPrefix("hey"));
//        System.out.println(newRandomIdWithPrefix("hey"));
//    }

    public RunId()
    {
    }

    public RunId(String id)
    {
        super(id);
    }
}
