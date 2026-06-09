import React, { createContext, useContext, useState } from 'react';

const lightColors = {
  background: '#ffffff',
  text: '#222222',
  card: '#f5f5f5',
  border: '#cccccc',
  input: '#ffffff',
  primary: '#2563eb',
  placeholder: '#777777',
};

const darkColors = {
  background: '#121212',
  text: '#f5f5f5',
  card: '#1f1f1f',
  border: '#444444',
  input: '#1f1f1f',
  primary: '#60a5fa',
  placeholder: '#aaaaaa',
};

const ThemeContext = createContext({
  isDark: false,
  colors: lightColors,
  toggleTheme: () => {},
});

export function ThemeProvider({ children }: any) {
  const [isDark, setIsDark] = useState(false);
  const colors = isDark ? darkColors : lightColors;

  const toggleTheme = () => {
    setIsDark((current) => !current);
  };

  return (
    <ThemeContext.Provider value={{ isDark, colors, toggleTheme }}>
      {children}
    </ThemeContext.Provider>
  );
}

export function useTheme() {
  return useContext(ThemeContext);
}
