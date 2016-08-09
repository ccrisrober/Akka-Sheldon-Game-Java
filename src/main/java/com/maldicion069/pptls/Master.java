// Copyright (c) 2016, maldicion069 (Cristian Rodríguez) <ccrisrober@gmail.con>
//
// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.package com.example

package com.maldicion069.pptls;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Cristian
 */
public class Master extends UntypedActor {

    protected int numPlayers;
    protected ActorRef listener;
    protected List<Player> players;
    protected List<Result> results;
    
    private final ActorRef workerRouter;
    
    public Master(int numPlayers, ActorRef listener) {
        this.numPlayers = numPlayers;
        this.listener = listener;
        this.players = new LinkedList<>();
        this.results = new LinkedList<>();
        
        workerRouter = this.getContext().actorOf(new Props(Player.class).withRouter(new RoundRobinRouter(numPlayers)), "workerRouter");
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof PlayGame) {
            this.results.clear();
            for(int i = 0; i < numPlayers; i++) {
                // Create players
                workerRouter.tell(new Hand(i), getSelf());
            }
        } else if (msg instanceof Result) {
            Result r = (Result) msg;
            this.results.add(r);
            if(this.results.size() == this.numPlayers) {
                // Send the result to the listener
                listener.tell(new RoundResult(this.results, getSelf()));
            }
        } else if (msg instanceof FinishGame) {
            // Stops this actor and all its supervised children
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }
    
}
