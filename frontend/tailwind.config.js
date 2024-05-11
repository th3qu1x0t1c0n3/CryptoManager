/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'port': 'var(--port-color)',
        'port-light': 'var(--port-light-color)',
        'port-yellow': 'var(--port-yellow-color)',
        'port-orange': 'var(--port-orange-color)',
        'port-red': 'var(--port-red-color)',
        'port-hover': 'var(--port-hover-color)',
      },
    },
  },
  plugins: [],
}