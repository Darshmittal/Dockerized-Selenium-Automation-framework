# Build stage: Compile the project
FROM maven:3.8.6-openjdk-11 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build without running tests
RUN mvn clean package -DskipTests

# Runtime stage: Use a full JDK image
FROM openjdk:11-jdk-slim

# Set working directory
WORKDIR /app

# Install Chrome and dependencies
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    libglib2.0-0 \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    && wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
    && dpkg -i google-chrome-stable_current_amd64.deb || apt-get install -f -y \
    && rm google-chrome-stable_current_amd64.deb \
    && rm -rf /var/lib/apt/lists/*

# Install Maven in the runtime stage
RUN apt-get update && apt-get install -y maven \
    && rm -rf /var/lib/apt/lists/*

# Copy the project files from the build stage
COPY --from=build /app /app

# Run tests when the container starts
CMD ["mvn", "test"]