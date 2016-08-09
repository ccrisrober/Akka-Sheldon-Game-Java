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
import com.maldicion069.pptls.Player.Positions;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Cristian
 */
public class Listener extends UntypedActor {

    private List<Result> filterData(List<Result> results, Positions type) {
        return results.parallelStream().filter(r -> r.getValue().equals(type)).collect(Collectors.toList());
    }
    
    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof RoundResult) {
            RoundResult result = (RoundResult) msg;

            List<Result> results = result.getResults();

            List<Result> rocks = filterData(results, Positions.Piedra);
            List<Result> papers = filterData(results, Positions.Papel);
            List<Result> scissors = filterData(results, Positions.Tijeras);
            List<Result> alligators = filterData(results, Positions.Lagarto);
            List<Result> spocks = filterData(results, Positions.Spock);

            List<Integer> r = new LinkedList<>();
            r.add(rocks.size());
            r.add(papers.size());
            r.add(scissors.size());
            r.add(alligators.size());
            r.add(spocks.size());

            long count = r.stream().filter(c -> c > 0).count();

            boolean winPiedra = false;
            boolean winPapel = false;
            boolean winTijeras = false;
            boolean winLagarto = false;
            boolean winSpock = false;

            if (count == 2) {
                // Buscamos las dos listas que tienen datos
                Positions p1 = null;
                Positions p2 = null;
                if (!rocks.isEmpty()) {
                    if (p1 == null) {
                        p1 = Positions.Piedra;
                    } else if (p2 == null) {
                        p2 = Positions.Piedra;
                    }
                }
                if (!papers.isEmpty()) {
                    if (p1 == null) {
                        p1 = Positions.Papel;
                    } else if (p2 == null) {
                        p2 = Positions.Papel;
                    }
                }
                if (!scissors.isEmpty()) {
                    if (p1 == null) {
                        p1 = Positions.Tijeras;
                    } else if (p2 == null) {
                        p2 = Positions.Tijeras;
                    }
                }
                if (!alligators.isEmpty()) {
                    if (p1 == null) {
                        p1 = Positions.Lagarto;
                    } else if (p2 == null) {
                        p2 = Positions.Lagarto;
                    }
                }
                if (!spocks.isEmpty()) {
                    if (p1 == null) {
                        p1 = Positions.Spock;
                    } else if (p2 == null) {
                        p2 = Positions.Spock;
                    }
                }
                if (p1.equals(p2)) {
                    System.out.println("EMPATE");
                } else {
                    System.out.println(Arrays.toString(r.toArray()));
                    if (p1.equals(Positions.Piedra)) {
                        if (p2.equals(Positions.Tijeras)) {
                            System.out.println("Piedra GANA CONTRA Tijeras");
                            winPiedra = true;
                        } else if (p2.equals(Positions.Papel)) {
                            System.out.println("Piedra PIERDE CONTRA Papel");
                            winPapel = true;
                        } else if (p2.equals(Positions.Lagarto)) {
                            System.out.println("Piedra APLASTA A Lagarto");
                            winPiedra = true;
                        } else if (p2.equals(Positions.Spock)) {
                            System.out.println("Piedra ES VAPORIZADA POR Spock");
                            winSpock = true;
                        }
                    } else if (p1.equals(Positions.Papel)) {
                        if (p2.equals(Positions.Piedra)) {
                            System.out.println("Papel TAPA A Piedra");
                            winPapel = true;
                        } else if (p2.equals(Positions.Tijeras)) {
                            System.out.println("Papel) ES CORTADO POR Tijeras)");
                            winTijeras = true;
                        } else if (p2.equals(Positions.Lagarto)) {
                            System.out.println("Papel ES DEVORADO POR Lagarto");
                            winLagarto = true;
                        } else if (p2.equals(Positions.Spock)) {
                            System.out.println("Papel DESAUTORIZA A Spock");
                            winPapel = true;
                        }
                    } else if (p1.equals(Positions.Tijeras)) {
                        if (p2.equals(Positions.Papel)) {
                            System.out.println("Tijeras GANA CONTRA Papel");
                            winTijeras = true;
                        } else if (p2.equals(Positions.Piedra)) {
                            System.out.println("Tijeras PIERDE CONTRA Piedra");
                            winPiedra = true;
                        } else if (p2.equals(Positions.Lagarto)) {
                            System.out.println("Tijeras DECAPITAN A Lagarto");
                            winTijeras = true;
                        } else if (p2.equals(Positions.Spock)) {
                            System.out.println("Tijeras SON ROTAS POR Spock");
                            winSpock = true;
                        }
                    } else if (p1.equals(Positions.Lagarto)) {
                        if (p2.equals(Positions.Piedra)) {
                            System.out.println("Lagarto ES APLASTADO POR Piedra");
                            winPiedra = true;
                        } else if (p2.equals(Positions.Tijeras)) {
                            System.out.println("Lagarto ES DECAPITADO POR Tijeras");
                            winTijeras = true;
                        } else if (p2.equals(Positions.Papel)) {
                            System.out.println("Lagarto DEVORA A Papel");
                            winLagarto = true;
                        } else if (p2.equals(Positions.Spock)) {
                            System.out.println("Lagarto ENVENENA A Spock");
                            winLagarto = true;
                        }
                    } else if (p1.equals(Positions.Spock)) {
                        if (p2.equals(Positions.Tijeras)) {
                            System.out.println("Spock ROMPE A Tijeras");
                            winSpock = true;
                        } else if (p2.equals(Positions.Papel)) {
                            System.out.println("Spock ES DESAUTORIZADO POR Papel");
                            winPapel = true;
                        } else if (p2.equals(Positions.Piedra)) {
                            System.out.println("Spock VAPORIZA A Piedra");
                            winSpock = true;
                        } else if (p2.equals(Positions.Lagarto)) {
                            System.out.println("Spock ES ENVENENADO POR Lagarto");
                            winLagarto = true;
                        }
                    }
                }
                main.vueltas--;
                System.out.println("PIEDRA: " + winPiedra + " PAPEL: " + winPapel + " TIJERAS: " + winTijeras + " LAGARTO: " + winLagarto + " SPOCK: " + winSpock);
            } else {
                System.out.println("EMPATE TOTAL");
            }
            if (main.vueltas > 0) {
                result.getRef().tell(new PlayGame());
            } else {
                result.getRef().tell(new FinishGame());
                getContext().stop(getSelf());
                getContext().system().shutdown();
            }
        } else {
            unhandled(msg);
        }
    }

}
