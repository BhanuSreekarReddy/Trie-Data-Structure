import java.util.*;

public class Trie {

	private class TrieNode{
		Map<Character, TrieNode> children;
		boolean isEndOfWord;
		
		public TrieNode()
		{
			children = new HashMap<>();
			isEndOfWord = false;
		}
		
	}
	private TrieNode root;
	
	public Trie()
	{
		root = new TrieNode();
	}
	
	public void insert(String word)
	{
		TrieNode current = root;
		for(int i=0;i<word.length();i++)
		{
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node==null)
			{
				node = new TrieNode();
				current.children.put(ch,node);
			}
			current = node;
		}
		current.isEndOfWord = true;
	}
	public boolean search(String word)
	{
		TrieNode current = root;
		for(int i=0;i<word.length();i++)
		{
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node==null)
				return false;
			current = node;
		}
		
		return current.isEndOfWord;
	}
	public String longestCommonPrefix()
	{
		StringBuilder prefix = new StringBuilder();
		TrieNode current = root;
		while(current!=null && current.children.size()==1 && !current.isEndOfWord)
		{
			Set<Character> set = current.children.keySet();
			
			String key = set.iterator().next().toString();
			TrieNode node = current.children.get(key.charAt(0));
			current = node;
			prefix.append(key.charAt(0));
		}
		
		return prefix.toString();
	}
	public List<String> autocomplete(String prefix)
	{
		List<String> res= new ArrayList<>();
		TrieNode current = root;
		for(int i=0;i<prefix.length();i++)
		{
			char ch = prefix.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node == null)
				return res;
			current = node;
		}
		if(current.isEndOfWord)
		{
			res.add(prefix);
			return res;
		}
		else
			allPrefixes(current,new StringBuilder(),0,res,prefix);
		
		return res;
		
	}
	public void allPrefixes(TrieNode root,StringBuilder sb,int pos,List<String> res,String prefix)
	{
		if(root==null)
			return;
		if(root.isEndOfWord)
		{
			res.add(prefix+sb.toString());
		}
		else
		{
			for(Map.Entry<Character, TrieNode> map: root.children.entrySet())
			{
				sb.insert(pos, map.getKey());
				allPrefixes(map.getValue(),sb,pos+1,res,prefix);
				sb.deleteCharAt(pos);
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Trie t= new Trie();
		t.insert("good");
		t.insert("god");
		t.insert("gold");
		t.insert("golang");
		List<String> res= t.autocomplete("go");
		for(int i=0;i<res.size();i++)
		{
			System.out.println(res.get(i));
		}
		System.out.println(t.longestCommonPrefix());
	}

}
