/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//chain/src/test/org/apache/commons/chain/impl/TestContextTestCase.java,v 1.4 2003/10/12 09:11:53 rdonkin Exp $
 * $Revision: 1.4 $
 * $Date: 2003/10/12 09:11:53 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.chain.impl;


import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.commons.chain.Context;


/**
 * Extension of <code>ContextBaseTestCase</code> to validate property
 * delegation.
 */

public class TestContextTestCase extends ContextBaseTestCase {


    // ------------------------------------------------------------ Constructors

    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public TestContextTestCase(String name) {
        super(name);
    }


    // ---------------------------------------------------- Overall Test Methods


    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() {
        context = createContext();
    }


    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() {
        return (new TestSuite(TestContextTestCase.class));
    }


    // ------------------------------------------------- Individual Test Methods


    // Test state of newly created instance
    public void testPristine() {

        super.testPristine();
        assertEquals("readOnly", (String) context.get("readOnly"));
        assertEquals("readWrite", (String) context.get("readWrite"));
        assertEquals("writeOnly", ((TestContext) context).returnWriteOnly());

    }


    // Test a read only property on the Context implementation class
    public void testReadOnly() {

        Object readOnly = context.get("readOnly");
        assertNotNull("readOnly found", readOnly);
        assertTrue("readOnly String",
                   readOnly instanceof String);
        assertEquals("readOnly value", "readOnly", readOnly);

        try {
            context.put("readOnly", "new readOnly");
            fail("Should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }
        assertEquals("readOnly unchanged", "readOnly",
                     (String) context.get("readOnly"));

    }


    // Test a read write property on the Context implementation class
    public void testReadWrite() {

        Object readWrite = context.get("readWrite");
        assertNotNull("readWrite found", readWrite);
        assertTrue("readWrite String",
                   readWrite instanceof String);
        assertEquals("readWrite value", "readWrite", readWrite);

        context.put("readWrite", "new readWrite");
        readWrite = context.get("readWrite");
        assertNotNull("readWrite found", readWrite);
        assertTrue("readWrite String",
                   readWrite instanceof String);
        assertEquals("readWrite value", "new readWrite", readWrite);

    }


    // Test a write only property on the Context implementation class
    public void testWriteOnly() {

        Object writeOnly = ((TestContext) context).returnWriteOnly();
        assertNotNull("writeOnly found", writeOnly);
        assertTrue("writeOnly String",
                   writeOnly instanceof String);
        assertEquals("writeOnly value", "writeOnly", writeOnly);

        context.put("writeOnly", "new writeOnly");
        writeOnly = ((TestContext) context).returnWriteOnly();
        assertNotNull("writeOnly found", writeOnly);
        assertTrue("writeOnly String",
                   writeOnly instanceof String);
        assertEquals("writeOnly value", "new writeOnly", writeOnly);

    }


    // ------------------------------------------------------- Protected Methods


    // Create a new instance of the appropriate Context type for this test case
    protected Context createContext() {
        return (new TestContext());
    }


}