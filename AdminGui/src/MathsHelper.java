import java.util.ArrayList;

public abstract class MathsHelper
	{
		//using the given hash function allows others to insert
		//a hash code into the password file and therefore change
		// the password. This stops that from happening.
		static public int privateHashFunction(String s, int key)
		{
			char[] chars = s.toCharArray();
			int hash = 0;
			int n = chars.length;
			for(int i = 0; i < s.length(); i++)
			{
				int next = chars[i];
				hash += next * Math.pow(key, n - 1 - i);
			}
			return hash;
		}
		static <T> ArrayList<T> shuffle(ArrayList<T> list)
		{
			ArrayList<T> output = new ArrayList<T>();
			boolean[] isSelected = new boolean[list.size()];
			for (boolean bool : isSelected)
			{
				bool = false;
			}
			
			for(int i = 0; i < list.size(); i++)
			{
				int nextIndex = (int)(Math.random() * list.size());
				
				if(!isSelected[nextIndex])
				{
					isSelected[nextIndex] = true;
					output.add(list.get(nextIndex));
				}
				else
				{
					i--;
				}
			}
			return output;
		}
		public static double mean(double[] nums)
		{
			double sum = 0;
			for (double num : nums)
			{
				sum += num;
			}
			return sum / nums.length;
		}
		public static double median(double[] nums)
		{
			int[] ranked = new int[nums.length];
			boolean[] available = new boolean[nums.length];
			for(boolean b : available)
			{
				b = true;
			}
			for(int i = 0; i < ranked.length; i++)
			{
				int index = 0;
				double smallest = nums[index];
				for(int j = 0; j < nums.length; j++)
				{
					if(available[j] && nums[j] < smallest)
					{
						smallest = nums[j];
						index = j;
					}
				}
				ranked[i] = index;
				available[index] = false;
			}
			return ranked[(int)Math.floor((nums.length + 1) / 2)];
		}
		public static double standardDeviation(double[] nums)
		{
			double mean = mean(nums);
			double var = 0;
			for(int i = 0; i < nums.length; i++)
			{
				var += Math.pow(nums[i] - mean, 2);
			}
			return Math.sqrt(var);
		}
		public static double skew(double[] nums)
		{
			double mean = mean(nums);
			double median = median(nums);
			double sd = standardDeviation(nums);
			return (mean - median) / sd;
		}
		private static int fact(int n)
		{
			int out = 1;
			for(int i = 1; i <= n; i++)
			{
				out *= i;
			}
			return out;
		}
		public static double poisson(double lambda, int n)
		{
			return Math.exp(-lambda) * Math.pow(lambda, n) / fact(n);
		}
		public static double cumulativePoisson(double lambda, int n)
		{
			double sum = 0;
			for(int i = 0; i <= n; i++)
			{
				sum += poisson(lambda, n);
			}
			return sum;
		}
	}