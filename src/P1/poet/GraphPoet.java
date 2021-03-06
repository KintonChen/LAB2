/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        //FileReader fr= new FileReader(corpus);
        BufferedReader br= new BufferedReader(new FileReader(corpus));
        String line="";
        String[] words;
        while ((line=br.readLine())!=null)
        {
            line= line.replace("."," ");
            words = line.split(" ");
            for(int i=0;i<words.length;i++)
            {
                graph.add(words[i].toLowerCase(Locale.ROOT));
                if(i>0)
                {
                    int preweight = graph.set(words[i-1],words[i],1);
                    if(preweight!=0)
                    {
                        graph.set(words[i-1],words[i],preweight+1);
                    }
                }

            }
        }
        //fr.close();
        br.close();
        checkRep();
    }
    
    // TODO checkRep
    private void checkRep()
    {
        Set<String> vertices_dup = graph.vertices();
        for (String vertex : vertices_dup)
            assert (vertex != null);
    }
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String preweight="";
        if(input.contains("."))
            preweight=".";
        String Input_cur = input.replace("."," ");
        String[] words = Input_cur.split(" ");
        Set<String> vers = graph.vertices();
        Map<String,Integer> sources,targets;
        String ans=words[0];
        Set<String> intersection;
        for(int i=1;i<words.length;i++)
        {
            if (!vers.contains(words[i - 1].toLowerCase()) || !vers.contains(words[i].toLowerCase())) {
                ans = ans + " " + words[i];
                continue;
            }
            targets = graph.targets(words[i - 1].toLowerCase());
            sources = graph.sources(words[i].toLowerCase());
            intersection = sources.keySet();
            intersection.retainAll(targets.keySet());
            if (intersection.isEmpty()) {
                ans = ans +  " " + words[i];
                continue;
            }
            int maxBridge = Integer.MIN_VALUE;
            String bridge = "";
            for (String key : intersection) {
                if (sources.get(key) + targets.get(key) > maxBridge) {
                    maxBridge = sources.get(key) + targets.get(key);
                    bridge = key;
                }
            }
            ans = ans +  " " + bridge + " " + words[i];
        }
        return ans+preweight;
    }
    
    // TODO toString()
    @Override public String toString()
    {
        return "an instance of GraphPoet, hashcode: " + this.hashCode();
    }

}
