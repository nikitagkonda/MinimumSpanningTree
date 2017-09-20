import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class MST_Nikita_Konda_50207717
{

	public static void main(String[] args) throws IOException {
		
		//HashMap for Adjacency List
		 HashMap<Integer,LinkedList<Edge>> adjList = new HashMap<Integer,LinkedList<Edge>>();
		 
		 //Input file
		 BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		
		 //Output file
		 PrintWriter writer = new PrintWriter("output.txt");
		
		 //Parsing the first line
		 String firstline = br.readLine();
		 String s[]=firstline.split(" ");
		 int n=Integer.parseInt(s[0]);
		 for (int i=1;i<=n;i++)
		 {
			 adjList.put(i, null);
		 }
		 String line= null;
		 
		 //Parsing the remaining lines
		 while((line=br.readLine())!= null)
		 {
			 if(line.trim().length()==0)
				continue;
			 String s1[]= line.split(" ");
			 int u = Integer.parseInt(s1[0]);
			 int v = Integer.parseInt(s1[1]);
			 double w = Double.parseDouble(s1[2]);
			 Edge e = new Edge();
			 e.setU(u);
			 e.setV(v);
			 e.setW(w);
			 LinkedList<Edge> list = new LinkedList<Edge>();
			 if(adjList.get(u)!=null)
				 list = adjList.get(u);
			 list.add(e);
			 adjList.put(u,list);
			 
			 e = new Edge();
			 e.setU(v);
			 e.setV(u);
			 e.setW(w);
			 list = new LinkedList<Edge>();
			 if(adjList.get(v)!=null)
				 list=adjList.get(v);
			 list.add(e);
			 adjList.put(v,list);
		 } 
		 br.close();
		 
		 //Resultant MST
		 LinkedList<Edge> result = new LinkedList<Edge>();
		 
		 //Visited nodes
		 LinkedList<Integer> visited = new LinkedList<Integer>();
		 
		 //Heap Data Structure
		 HeapDataStructure h =new HeapDataStructure(n);
		 for(int i=1;i<=n;i++)
			 h.insert(i);

		 //Sum of all the weights in the MST 
		 double sum=0;
		 while(h.Heap.size()!=1)
		 {
			 int x=h.extract_min();
	 
			 if(visited.contains(x))
				 continue; 
			 
			 visited.add(x);
			 if(x>1)
			 { 
				 Edge e = new Edge();
				 e.setU(h.parent.get(x));
				 e.setV(x);
				 sum=sum + h.d.get(x);
				 result.add(e);
			}
			 
			 for(int i=0;i<adjList.get(x).size();i++)
			 {
				 double wt=adjList.get(x).get(i).getW();
				 int v = adjList.get(x).get(i).getV();
				 if(wt<h.d.get(v))
				 {
					 h.decrease_key(v,wt);
					 h.parent.put(v, x);
				 }
			 }
		 }
		 
		 writer.println((int)sum);
		 for(int i=0;i<result.size();i++)
		 {
			 writer.println(result.get(i).getU()+" "+result.get(i).getV());
		 }
		 writer.close();
	}

}

class HeapDataStructure {
	private int size;
	ArrayList<Integer> Heap;
	HashMap<Integer,Double> d = new HashMap<Integer,Double>(); //Minimum distances of vertices from MST
	HashMap<Integer,Integer> parent = new HashMap<Integer, Integer>(); //Parent of vertices
	HashMap<Integer,Integer> pos = new HashMap<Integer,Integer>(); //Position of vertices

	
	public HeapDataStructure(int size)
    {
        Heap = new ArrayList<Integer>(size+1);
        d.put(1, 0.0);
    	for (int i=2;i<=size;i++)
	   	 {
	   		 d.put(i, Double.MAX_VALUE);
	   	 }
    	
    	Heap.add(null);
    }
	
	public void insert(int v)
	{
		size=size+1;
		Heap.add(v);
		heapify_up(size);
		pos.put(v,Heap.indexOf(v));
	}

	private void heapify_up(int i) {
		while(i>1)
		{ 
			int j = i/2;
			if (d.get(Heap.get(i)) < d.get(Heap.get(j))) {
				 int temp=Heap.get(i);
				 Heap.set(i, Heap.get(j));
				 Heap.set(j, temp);
				 pos.put(Heap.get(i), i);
				 pos.put(Heap.get(j), j);
				 i=j;
			}
			else 
				break;
		}
	}
	
	public int extract_min()
	{
		int temp = Heap.get(1);
		pos.put(Heap.get(1), -1);
		pos.put(Heap.get(size), 1);
		Heap.set(1, Heap.get(size));
		Heap.remove(size);
		size=size-1;
		if(size>= 1) 
			heapify_down(1);
		return temp;
	}

	private void heapify_down(int i) {
		while((2*i)<=size)
		{ 
			int j;
			if ((2*i)==size || (d.get(Heap.get(2*i))<=d.get(Heap.get((2*i)+1))))
				j=2*i;
			else
				j=2*i+1;
			if (d.get(Heap.get(j)) < d.get(Heap.get(i)))
			{
				int temp=Heap.get(i);
				Heap.set(i, Heap.get(j));
				Heap.set(j, temp);
				pos.put(Heap.get(i), i);
				pos.put(Heap.get(j), j);
				i=j;
			}
			else
				break;
		}
	}
	
	public void decrease_key(int v, double keyvalue)
	{
		d.put(v, keyvalue);
		heapify_up(pos.get(v));
	}
	
}

 class Edge {
	private Integer u;
	private Integer v;
	private Double w;
	public Integer getU() {
		return u;
	}
	public void setU(Integer u) {
		this.u = u;
	}
	public Integer getV() {
		return v;
	}
	public void setV(Integer v) {
		this.v = v;
	}
	public Double getW() {
		return w;
	}
	public void setW(Double w) {
		this.w = w;
	}
}

