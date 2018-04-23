class questionStats
	{
		String question;
		double[] answers = new double[4];
		String[] answerString = new String[4];
		int correct;
		double resetProportion;
		
		questionStats(String question, String[] answerStrings, double[] answers, int correct, double resetProportion)
		{
			this.question = question;
			this.answers = answers;
			this.answerString = answerStrings;
			this.correct = correct;
			this.resetProportion = resetProportion;
		}
		
		double getAnswer1()
		{
			return answers[0];
		}
		double getAnswer2()
		{
			return answers[1];
		}
		double getAnswer3()
		{
			return answers[2];
		}
		double getAnswer4()
		{
			return answers[3];
		}
		
		String getAnswerString1()
		{
			if(correct == 0)
			{
				return answerString[0] + " (correct)";
			}
			return answerString[0];
		}
		String getAnswerString2()
		{
			if(correct == 1)
			{
				return answerString[1] + " (correct)";
			}
			return answerString[1];
		}
		String getAnswerString3()
		{
			if(correct == 2)
			{
				return answerString[2] + " (correct)";
			}
			return answerString[2];
		}
		String getAnswerString4()
		{
			if(correct == 3)
			{
				return answerString[3] + " (correct)";
			}
			return answerString[3];
		}
		
		int getCorrect()
		{
			return correct;
		}
		double getResetProportion()
		{
			return resetProportion;
		}
	}