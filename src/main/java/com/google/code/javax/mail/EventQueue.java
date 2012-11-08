/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail;

import java.util.Vector;

import com.google.code.javax.mail.event.MailEvent;

/**
 * Package private class used by Store & GmailFolder to dispatch events.
 * This class implements an event queue, and a dispatcher thread that
 * dequeues and dispatches events from the queue.
 *
 * Pieces stolen from sun.misc.Queue.
 *
 * @author	Bill Shannon
 */
class EventQueue implements Runnable {

    static class QueueElement {
	QueueElement next = null;
	QueueElement prev = null;
	MailEvent event = null;
	Vector vector = null;

	QueueElement(MailEvent event, Vector vector) {
	    this.event = event;
	    this.vector = vector;
	}
    }

    private QueueElement head = null;
    private QueueElement tail = null;
    private Thread qThread;

    public EventQueue() {
	qThread = new Thread(this, "JavaMail-EventQueue");
	qThread.setDaemon(true);  // not a user thread
	qThread.start();
    }

    /**
     * Enqueue an event.
     */
    public synchronized void enqueue(MailEvent event, Vector vector) {
	QueueElement newElt = new QueueElement(event, vector);

	if (head == null) {
	    head = newElt;
	    tail = newElt;
	} else {
	    newElt.next = head;
	    head.prev = newElt;
	    head = newElt;
	}
	notifyAll();
    }

    /**
     * Dequeue the oldest object on the queue.
     * Used only by the run() method.
     *
     * @return    the oldest object on the queue.
     * @exception java.lang.InterruptedException if another thread has
     *              interrupted this thread.
     */
    private synchronized QueueElement dequeue()
				throws InterruptedException {
	while (tail == null)
	    wait();
	QueueElement elt = tail;
	tail = elt.prev;
	if (tail == null) {
	    head = null;
	} else {
	    tail.next = null;
	}
	elt.prev = elt.next = null;
	return elt;
    }

    /**
     * Pull events off the queue and dispatch them.
     */
    public void run() {
	QueueElement qe;

	try {
	    loop:
	    while ((qe = dequeue()) != null) {
		MailEvent e = qe.event;
		Vector v = qe.vector;

		for (int i = 0; i < v.size(); i++)
		    try {
			e.dispatch(v.elementAt(i));
		    } catch (Throwable t) {
			if (t instanceof InterruptedException)
			    break loop;
			// ignore anything else thrown by the listener
		    }

		qe = null; e = null; v = null;
	    }
	} catch (InterruptedException e) {
	    // just die
	}
    }

    /**
     * Stop the dispatcher so we can be destroyed.
     */
    void stop() {
	if (qThread != null) {
	    qThread.interrupt();	// kill our thread
	    qThread = null;
	}
    }
}
