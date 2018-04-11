package bigcloneeval.detection.part2.tasks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.SystemUtils;


import bigcloneeval.detection.part2.util.StreamGobbler;

public class DetectClones {
	
	public static void main(String args[]) {
		
		boolean fullclean = false;
		Path dataset = Paths.get("ijadataset/bcb_reduced/").toAbsolutePath();
		
	// Tool Runner
		Path toolrunner;		
		toolrunner = Paths.get("upload-dir/"+args[0]).toAbsolutePath();
		System.out.println(toolrunner);
		
		if(!Files.exists(toolrunner)) {
			System.err.println("Tool runner does not exist.");
			return;
		}
		if(!Files.isExecutable(toolrunner)) {
			System.err.println("Tool runner is not an executable.");
			return;
		}
		
	// Output File
		Path output;
		output = new File(args[1]).toPath().toAbsolutePath();
		//System.out.println(output);
		try {
			Files.deleteIfExists(output);
			Files.createDirectories(output.getParent());
			Files.createFile(output);
		} catch (Exception e) {
			System.err.println("Failed to create output file.");
			e.printStackTrace(System.err);
			return;
		}
		
	// Scratch Directory
		Path scratchDirectory;
		if(args.length>2 && !args[2].equals("")) {
			scratchDirectory = new File(args[1]).toPath().toAbsolutePath();
			if(Files.exists(scratchDirectory)) {
				System.err.println("Scratch directory already exists.  Must specify a new one (to protect against accidental data loss).");
				return;
			}
			try {
				Files.createDirectories(scratchDirectory);
			} catch (Exception e) {
				System.err.println("Failed to create scratch directory.");
				return;
			}
		} else {
			fullclean = true;
			try {
				scratchDirectory = Files.createTempDirectory("DetectClones");
			} catch (IOException e) {
				System.err.println("Failed to create scratch directory.");
				return;
			}
		}
		
	// Max-Files
		int maxFiles = Integer.MAX_VALUE;
		if(args.length>3 && !args[3].equals("")) {
			try {
				maxFiles = Integer.parseInt(args[3]);
				if(maxFiles <= 0)
					throw new IllegalArgumentException();
			} catch (Exception e) {
				System.err.println("Invalid value for max-files.");
				return;
			}
		}
		
	// Cleanup
		boolean cleanup = true;
		if(args.length>4 && !args[4].equals("")) {
			if(args.length>2 && !args[2].equals("")) {
				System.err.println("No cleanup only compatible with custom scratch directory.");
				return;
			}
			cleanup = false;
		}
		
		try {
			DetectClones.detect(output, dataset, toolrunner, scratchDirectory, maxFiles, cleanup);
		} catch (IOException e1) {
			System.err.println("An exeception occured during detection:");
			e1.printStackTrace(System.err);
			return;
		}
	
	// Cleanup
		if(fullclean) {
			try {
				FileUtils.deleteDirectory(scratchDirectory.toFile());
			} catch (IOException e) {
				System.err.println("Failed to delete scratch (temporary) directory, please do so manually: " + scratchDirectory.toString());
			}
		}
		
	}
	
	public static void detect(Path output, Path dataset, Path tool, Path scratchdir, int maxFiles, boolean cleanup) throws IOException {
	
		output = output.toAbsolutePath();
		dataset = dataset.toAbsolutePath();
		tool = tool.toAbsolutePath();
		scratchdir = scratchdir.toAbsolutePath();
		
	// Open Output for Writing
		BufferedWriter writer = new BufferedWriter(new FileWriter(output.toFile()));
		
	// Build list of inputs
		List<Path> inputs = new ArrayList<Path>();
		for(File file : dataset.toFile().listFiles(File::isDirectory)) {//FileUtils.listFilesAndDirs(dataset.toFile(), DirectoryFileFilter.INSTANCE, null)) {
			inputs.add(file.toPath());
		}
		Collections.sort(inputs);
		Collections.reverse(inputs);
		
	// Run Detection
		for(Path input : inputs) {
			System.out.println("Detecting clones in: " + input.toAbsolutePath());
			if(FileUtils.listFiles(input.toFile(), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).size() > maxFiles) {
				detect(tool, input, writer, scratchdir, maxFiles, cleanup);
			} else {
				try {
					int retval = detect(tool,input,writer);
					if(0 != retval) {
						System.err.println("Execution for input: " + input + " had a non-zero return value: " + retval + ".");
					}
				} catch (InterruptedException | IOException e) {
					System.err.println("Execution for input: " + input + " failed with exception: ");
					e.printStackTrace(System.err);
				}
			}
		}
		
	// Close Output
		writer.flush();
		writer.close();
	}
	
	public static int detect(Path tool, Path input, Writer out) throws IOException, InterruptedException {
		ProcessBuilder pb;
		if(SystemUtils.IS_OS_WINDOWS) {
			String [] exec = {"cmd.exe", "/c", tool.toString() + " " + input.toString()};
			pb = new ProcessBuilder(exec);
		} else {
			String [] exec = {tool.toString(),input.toString()};
			pb = new ProcessBuilder(exec);
		}
		
		Process p = pb.start();
		new StreamGobbler(p.getErrorStream()).start();
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while((line = br.readLine()) != null) {
			line = line.trim();
			if(!line.equals("") && line.contains(","))
			{
				String[] token = line.split(",");
				if(token.length == 8)
				{
					out.write(line + "\n");
				}
			}
		}
		br.close();
		int retval = p.waitFor();
		return retval;
	}
	
	public static void detect(Path tool, Path input, Writer out, Path scratchdir, int maxFiles, boolean cleanup) throws IOException {
	// Partition
		Path tmpdir = Files.createDirectories(scratchdir.resolve(input.getFileName().toString() + "_partition"));
		DetectClones.partition(input, tmpdir, maxFiles);
		
	// Run for each partition
		for(File partition : tmpdir.toFile().listFiles()) {
			System.out.println("\tExecuting for partition: " + partition.getAbsolutePath());
			try {
				int retval = detect(tool, partition.toPath(), out);
				if(0 != retval) {
					System.err.println("Execution for input: " + input + " partition: " + partition.getName() + " had non-zero return value.");
				}
			} catch (Exception e) {
				System.err.println("Execution for input: " + input + " partition: " + partition.getName() + " failed with exception:");
				e.printStackTrace(System.err);
			}
		}
		
	// Cleanup
		if(cleanup) {
			try {
				FileUtils.deleteDirectory(tmpdir.toFile());
			} catch (Exception e) {
				System.err.println("Failed to delete a temporary directory, please do so manually: " + tmpdir.toAbsolutePath());
			}
		}
	}
	
	public static void partition(Path dir, Path split, int maxfiles) throws IOException {
	// Get and Shuffle the files
		List<File> files = new ArrayList<File>(FileUtils.listFiles(dir.toFile(), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE));
		Collections.shuffle(files);
		
	// halve the maxfiles to get the max size of each partition
		maxfiles = Math.max((int) Math.ceil(1.0*maxfiles/2),1);
		
	// Number of partitions
		int numpartitions = (int) Math.ceil(1.0*files.size()/maxfiles);
		
	// Split files into the partitions
		List<List<File>> partitions = new ArrayList<List<File>>(numpartitions);
		int count = 0;
		for(int i = 0; i < numpartitions; i++) {
			partitions.add(new ArrayList<File>(maxfiles));
			List<File> partition = partitions.get(i);
			for(int j = 0; j < maxfiles && count < files.size(); j++) {
				partition.add(files.get(count));
				count++;
			}
		}
		
	// Build each pair of partitions on disk, considering symmetry
		for(int i = 0; i < partitions.size(); i++) {
			List<File> partition1 = partitions.get(i);
			for(int j = i+1; j < partitions.size(); j++) {
				List<File> partition2 = partitions.get(j);
				
				Path idir = split.resolve(i + "-" + j);
				Files.createDirectories(idir);
				
				for(int k = 0; k < partition1.size(); k++) {
					Path ofile = partition1.get(k).getCanonicalFile().toPath();
					Path nfile = idir.resolve(dir.relativize(ofile));
					Files.createDirectories(nfile.getParent());
					Files.copy(ofile, nfile);
				}
				
				for(int k = 0; k < partition2.size(); k++) {
					Path ofile = partition2.get(k).getCanonicalFile().toPath();
					Path nfile = idir.resolve(dir.relativize(ofile));
					Files.createDirectories(nfile.getParent());
					Files.copy(ofile, nfile);
				}
					
			}
		}	
	}
	
}
