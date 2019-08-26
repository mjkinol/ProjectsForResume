#include <string>
#include <map>


struct AlphaStrComp {
  bool operator() (const std::string& lhs, const std::string& rhs)
  { // Uses string's built in operator<
    // to do lexicographic (alphabetical) comparison
    return (lhs < rhs);
  }
};

struct LengthStrComp {
  bool operator()(const std::string& lhs, const std::string& rhs)
  { // Compares strings by size;
    // Breaks ties by lexicographic comparison
    return (   (lhs.size() < rhs.size())
            || (lhs.size() == rhs.size() && lhs < rhs));
  }
};

struct NumStrComp {
  bool operator()(const std::string& lhs, const std::string& rhs)
  { // Compares strings by sum of individual values;
    int sumL = 0;
    int sumR = 0;
    std::map<char, int> values;
    std::map<char, int>::iterator it;
    for(unsigned int i = 0; i < rhs.size(); i++)
    {
      int curr = int(rhs[i]);
      if(curr > 47 && curr < 58) sumR += (curr-48);
      if(curr > 64 && curr < 91) sumR += (curr-55);
      if(curr > 96 && curr < 123) sumR += (curr-87);
    }
    for(unsigned int i = 0; i < lhs.size(); i++)
    {
      int curr = int(lhs[i]);
      if(curr > 47 && curr < 58) sumL += (curr-48);
      if(curr > 64 && curr < 91) sumL += (curr-55);
      if(curr > 96 && curr < 123) sumL += (curr-87);
    }
    return (sumL < sumR || ((sumL == sumR) && lhs < rhs));
  }
};