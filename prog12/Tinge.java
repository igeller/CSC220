package prog12;
import java.util.*;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Tinge implements SearchEngine{
	HardDisk <PageFile> pageFiles = new HardDisk<PageFile>();
	PageTrie url2index = new PageTrie();
	
	HardDisk<List<Long>> wordFiles = new HardDisk<List<Long>>();
	WordTable word2index = new WordTable();
	
	
	
	
	
	public Long indexPage(String url) {
		Long index = pageFiles.newFile();
		PageFile pageFile = new PageFile(index, url);
		System.out.println("indexing page " + pageFile);
		pageFiles.put(index, pageFile);
		url2index.put(url, index);
		
		return index;
	}
	
	public Long indexWord(String word) {
		Long index = wordFiles.newFile();
		List<Long> pages = new ArrayList<Long>();
		
    	System.out.println("indexing word " + index + "(" + word + ")" + pages);
		wordFiles.put(index, pages);
		word2index.put(word, index);

		return index;
	}
	
	
	

	public void gather(Browser browser, List<String> startingURLs) {
		System.out.println("gather " + startingURLs);
		Queue<Long> pageQueue = new ArrayDeque<Long>();
		
	
		for(String url: startingURLs) {
			if(!url2index.containsKey(url)) {
				Long tempIndex = indexPage(url);
				pageQueue.offer(tempIndex);				
			}
		}
		
		
		while(!pageQueue.isEmpty()) {
			System.out.println("queue " + pageQueue);

			Long pageIndex = pageQueue.poll();
			PageFile pageFile = pageFiles.get(pageIndex);
			
			System.out.println("dequeued " + pageFile);
			
			
			if(browser.loadPage(pageFile.url)) {
				List<String> browserUrls = browser.getURLs();
				Set<Long> pageIndices = new HashSet<Long>();
				Set<Long> wordIndicies = new HashSet<Long>();
				Long bIndex;
				
				
				System.out.println("urls " + browserUrls);
				
				
			
				for(String url: browserUrls) {
					
					if(!url2index.containsKey(url)) {
						bIndex = indexPage(url);
						pageQueue.offer(bIndex);
					}
					
					else{
						bIndex = url2index.get(url);
					}
					pageIndices.add(bIndex);
				}
				
				for(Long index: pageIndices) {
					pageFiles.get(index).incRefCount();
					System.out.println("inc ref " + pageFiles.get(index));
				}
				System.out.println("words "+ browser.getWords());
				
				
				
				for(String word: browser.getWords()) {
					
					if(!word2index.containsKey(word)) {
						bIndex = indexWord(word);
					}
					else {
						bIndex = word2index.get(word);
					}
//					
//					
					if(!wordIndicies.contains(bIndex)){
						wordIndicies.add(bIndex);
						wordFiles.get(bIndex).add(pageIndex);
						System.out.println("add page "+ bIndex +"("+ word +")"+wordFiles.get(bIndex));
					}
				}
			}
		}
		System.out.println("pageFiles\n" + pageFiles);
		System.out.println("url2index\n" + url2index);
		System.out.println("wordFiles\n" + wordFiles);
		System.out.println("word2index\n" + word2index);
		url2index.write(pageFiles);
		word2index.write(wordFiles);
		

	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		return new String[0];
	}
	

	
}