package com.hrms.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

//import org.apache.commons.lang.exception.ExceptionUtils;

public class TrySome {

	public static void copyTFile(String Source) throws IOException {
		try (
				// FileChannel in = new FileInputStream( "D:\\javaprogram.zip" ).getChannel();
				// FileChannel out = new FileOutputStream( "G:\\myzip.zip" ).getChannel() )
				FileChannel in = new FileInputStream("F:\\Software\\" + Source).getChannel();
				FileChannel out = new FileOutputStream("\\\\192.168.2.96\\java_team\\" + Source).getChannel()) {

			out.transferFrom(in, 0, in.size());

		}

	}

	public static void copyMap(String Source) {
		try {
			FileChannel in = new FileInputStream("F:\\Software\\" + Source).getChannel();
			File file = new File("\\\\192.168.2.96\\java_team\\" + Source);

			// Delete the file; we will create a new file
			file.delete();

			// Get file channel in readonly mode
			FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

			// Get direct byte buffer access using channel.map() operation
			MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096 * 8 * 8);

			// Write the content using put methods
			buffer.put(buffer.get());
		} catch (Exception e) {

		}

	}

	public static void copy(String Source) throws IOException {

		try {
			FileChannel in = new FileInputStream("F:\\Software\\" + Source).getChannel();
			FileChannel out = new FileOutputStream("\\\\192.168.2.96\\java_team\\" + Source).getChannel();
			try {
				// Transfer bytes from in to out
				ByteBuffer buf = ByteBuffer.allocate(1024 * 1024);
				int bytesCount;
				while ((bytesCount = in.read(buf)) > 0) { // Read data from file into ByteBuffer
					// flip the buffer which set the limit to current position, and position to 0.
					buf.flip();
					out.write(buf); // Write data from ByteBuffer to file
					buf.clear(); // For the next read
				}
				System.out.println(buf);
				System.out.println(in.size());
			} finally {
				out.close();
				in.close();

			}
		} finally {
		}
	}

	public static void copyFile(String Source) throws IOException {

		Path sourceFile = Paths.get("F:\\Software\\" + Source);
		Path targetFile = Paths.get("\\\\192.168.2.96\\java_team\\" + Source);

		try {
			Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			System.err.format("I/O Error when copying file");
		}
	}

	static void setFilePermission(String filePath) throws IOException {
		File file = new File(filePath);

		// set application user permissions to 455
		file.setExecutable(false);
		file.setReadable(false);
		file.setWritable(true);

		// change permission to 777 for all the users
		// no option for group and others
		file.setExecutable(true, false);
		file.setReadable(true, false);
		file.setWritable(true, false);

		// using PosixFilePermission to set file permissions 777
		Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
		// add owners permission
		perms.add(PosixFilePermission.OWNER_READ);
		perms.add(PosixFilePermission.OWNER_WRITE);
		perms.add(PosixFilePermission.OWNER_EXECUTE);
		// add group permissions
		perms.add(PosixFilePermission.GROUP_READ);
		perms.add(PosixFilePermission.GROUP_WRITE);
		perms.add(PosixFilePermission.GROUP_EXECUTE);
		// add others permissions
		perms.add(PosixFilePermission.OTHERS_READ);
		perms.add(PosixFilePermission.OTHERS_WRITE);
		perms.add(PosixFilePermission.OTHERS_EXECUTE);

		Files.setPosixFilePermissions(Paths.get(filePath), perms);
	}

	public static void copyFile() {
		String inputSource = "E:\\Vijay\\Books\\netbeans-8.0.1-windows.exe";
		String outputSource = "\\netbeans-8.0.1-windows.exe";

		try {

			// update file permission for input file
			/* setFilePermission(inputSource); */

			FileChannel in = new FileInputStream(inputSource).getChannel();

			// Get direct byte buffer access using channel.map() operation
			MappedByteBuffer inputBuffer = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());

			File file = new File("\\\\192.168.2.96\\java_trainee\\" + outputSource);

			// Delete the file; we will create a new file
			file.delete();

			// Get file channel in readonly mode
			FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

			String pathname = "\\\\.\\GLOBALROOT\\ArcName\\multi(0)disk(0)rdisk(0)partition(5)";

			Path diskRoot = (new File(pathname)).toPath();

			FileChannel fc = FileChannel.open(diskRoot, StandardOpenOption.READ, StandardOpenOption.WRITE);

			// Get direct byte buffer access using channel.map() operation
			MappedByteBuffer outputBuffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, in.size());

			// Write the content using put methods
			byte[] arr = new byte[inputBuffer.remaining()];
			inputBuffer.get(arr);
			outputBuffer.put(arr);
		} catch (Exception e) {
			// System.out.println("Root Cause : " + ExceptionUtils.getRootCauseMessage(e));
			System.out.println("Actual Stack : ");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		multiPartCopy();
	}

	public static void wordScroll() throws IOException, InterruptedException {
		StringBuilder word = null;

		InputStream in = System.in;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		word = new StringBuilder(reader.readLine());
		int wordLength = word.length();

		for (int i = 0; i < 100; i++) {
			System.out.print(word);
			Thread.sleep(100);
			for (int j = 0; j < wordLength; j++) {
				System.out.print("\b");
			}
			word.insert(0, " ");
			wordLength++;
		}

	}

	public static int randomNumberInRange(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	public static int[][] generateUniqueSequences(int requiredSum, int sequenceLimit, int variablesCount, int min,
			int max) {
		int[][] sequences = new int[sequenceLimit + 1][sequenceLimit];
		for (int i = 0; i < requiredSum; i++) {
			int[] sequence = new int[sequenceLimit];

			for (int j = 0; j < variablesCount; j++) {
				sequence[j] = randomNumberInRange(min, max);
			}

			sequences[i] = sequence;
		}
		return sequences;
	}

	public static void sumAlgorithm() {
		try {
			int sequenceLimit = 4;
			int a = 1;
			int b = 2;
			int requiredSum = 3;
			int variablesCount = 2;
			int min = 1;
			int max = 2;
			int sum = 3;
			int[][] sequences = null;
			Boolean uniqueSequenceGenerationFlag = Boolean.TRUE;
			
			while(uniqueSequenceGenerationFlag) {
				sequences = generateUniqueSequences(requiredSum, sequenceLimit, variablesCount, min, max);
				
				for(int i=0; i<sequences.length-1;i++) {
					if(sequences[i].equals(sequences[i+1])) {
						uniqueSequenceGenerationFlag = Boolean.TRUE;
						break;
					}
				}
				
				System.out.println(sequences);
				uniqueSequenceGenerationFlag=Boolean.FALSE;
			}

			//System.out.println(a);
			//System.out.println(b);

			//int a = randomNumberInRange(1, sum);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void displayWebContent() {
		String hostName = "http://www.google.com";
		PrintWriter pr = null;

		URL u;

		try {
			u = new URL(hostName);
			URLConnection uc = u.openConnection();
			InputStream raw = uc.getInputStream();
			// autoclose
			InputStream buffer = new BufferedInputStream(raw);
			// chain the InputStream to a Reader
			Reader reader = new InputStreamReader(buffer);
			int c;
			while ((c = reader.read()) != -1) {
				System.out.print((char) c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// System.out.println(ExceptionUtils.getRootCauseMessage(e));
		}
	}
	
	public static void multiPartCopy() {
		String inputSource = "E:\\Vijay\\Books\\netbeans-8.0.1-windows.exe";
		String outputSource = "\\netbeans-8.0.1-windows.exe";

		try {

			// update file permission for input file
			/* setFilePermission(inputSource); */

			FileInputStream in = new FileInputStream(inputSource);
			
			byte[] inputBytes = IOUtils.toByteArray(in);
			long readIndex = 0;
			byte[][] byteArrays = new byte[(inputBytes.length/1024)+2][1024];
			long byteArraysIndex = 0;
			
			// split input byte array to 1MB arrays
			while (inputBytes.length!=readIndex) {
				byte[] newByteArray = new byte[1024];
				int index;
				for(index=0; index<1024; index++) {
					newByteArray[index] = inputBytes[(int) readIndex];
					readIndex++;
				}
				
				byteArrays[(int) byteArraysIndex] = newByteArray; 
				byteArraysIndex++;
			}
			
			File file = new File("\\\\192.168.2.96\\Temp_Share\\" + outputSource);

			// Delete the file; we will create a new file
			file.delete();

			// Get file channel in readonly mode
			FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

			// Get direct byte buffer access using channel.map() operation
			MappedByteBuffer outputBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, inputBytes.length);

			// Write the content using put methods
			for(byte[] byteArray : byteArrays) {
				outputBuffer.put(byteArray);
			}
			
		} catch (Exception e) {
			System.out.println("Root Cause : " + ExceptionUtils.getRootCauseMessage(e));
			System.out.println("Actual Stack : ");
			e.printStackTrace();
		}
	}

}
