/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        customColor1: "#F5F8FF",
        customColor2: "#EAF0FF",
        customColor3: "#C2E5FF",
        customColor4: "#404040",
        customColor5: "#344A5D",
        customColor6: "#6D8AA1",
        customColor7: "#0879CF",
        customColor8: "#6DC0FF",
      },
    },
  },
  plugins: [],
};
