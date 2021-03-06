/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.switchyard.quickstarts.rest.binding;

import java.io.IOException;

import org.switchyard.test.mixins.HTTPMixIn;

/**
 * Client for RESTEasy binding.
 *
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2012 Red Hat Inc.
 */
public class RESTEasyBindingClient {

    private static final String BASE_URL = "http://localhost:8080/rest-binding";

    public static void main(String[] args) throws Exception {
        String command =  null;
        if (args.length == 0) {
            System.out.println("Usage: RESTEasyBindingClient new|get|add|del [orderId] [itemId]");
            System.out.println("  new - create a new Order");
            System.out.println("  get - retreive an Order");
            System.out.println("  add - add an item to an Order");
            System.out.println("  del - delete an item from an Order");
            System.out.println("\t CamelCxfRsBindingClient new");
            System.out.println("\t CamelCxfRsBindingClient get 1");
            System.out.println("\t CamelCxfRsBindingClient add 1 1 10");
            System.out.println("\t CamelCxfRsBindingClient del 1 3");
            return;
        } else {
            command = args[0];
            HTTPMixIn http = new HTTPMixIn();
            http.initialize();
            if (command.equals("new")) {
                String response = http.sendString(BASE_URL + "/inventory", "", HTTPMixIn.HTTP_GET);
                if (response.equals("false")) {
                    http.sendString(BASE_URL + "/inventory/create", "", HTTPMixIn.HTTP_OPTIONS);
                }
                System.out.println(http.sendString(BASE_URL + "/order", "", HTTPMixIn.HTTP_POST));
            } else if (command.equals("get")) {
                if (args.length != 2) {
                    System.out.println("No orderId found!");
                    System.out.println("Usage: get <orderId>");
                }
                System.out.println(http.sendString(BASE_URL + "/order/" + args[1], "", HTTPMixIn.HTTP_GET));
            } else if (command.equals("add")) {
                if (args.length < 2) {
                    System.out.println("No orderId found!");
                    System.out.println("Usage: get <orderId> <itemId> <quantity>");
                }
                if (args.length < 3) {
                    System.out.println("No itemId found!");
                    System.out.println("Usage: get <orderId> <itemId> <quantity>");
                }
                if (args.length < 4) {
                    System.out.println("No quantity found!");
                    System.out.println("Usage: get <orderId> <itemId> <quantity>");
                }
                String order = "<order>"
                               + "    <orderId>" + args[1] + "</orderId>"
                               + "    <orderItem>"
                               + "        <item>"
                               + "            <itemId>" + args[2] + "</itemId>"
                               + "         </item>"
                               + "         <quantity>" + args[3]+ "</quantity>"
                               + "     </orderItem>"
                               + "</order>";
                System.out.println(http.sendString(BASE_URL + "/order/item", order, HTTPMixIn.HTTP_PUT));
            } else if (command.equals("del")) {
                if (args.length < 2) {
                    System.out.println("No orderId found!");
                    System.out.println("Usage: get <orderId> <itemId>");
                }
                if (args.length < 3) {
                    System.out.println("No itemId found!");
                    System.out.println("Usage: get <orderId> <itemId>");
                }
                System.out.println(http.sendString(BASE_URL + "/order/" + args[1] + ":" + args[2], "", HTTPMixIn.HTTP_DELETE));
            }
        }
    }
}
