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

import akka.actor.UntypedActor;
import java.util.Random;

/**
 *
 * @author Cristian
 */
public class Player extends UntypedActor {
    protected static Random r;
    static {
        r = new Random();
    }
    
    public enum Positions {
        Piedra, Papel, Tijeras, Lagarto, Spock;
    }
    
    public Positions readValue() {
        return Positions.values()[r.nextInt(5)];
    }
    
    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Hand) {
            Hand h = (Hand) msg;
            Thread.sleep(Math.abs(r.nextInt(100)));
            getSender().tell(new Result(h.getId(), readValue()), getSelf());
        } else if (msg instanceof FinishGame) {
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }
    
}
